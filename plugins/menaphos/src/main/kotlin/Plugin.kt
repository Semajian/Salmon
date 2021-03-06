import kraken.plugin.PluginBase
import kraken.plugin.api.*
import shared.Filters
import shared.StaticEntity
import shared.enums.Action
import shared.enums.Skill
import shared.extensions.TypeCasts.toVector2i
import utilities.Time
import kotlin.math.max

class Plugin: PluginBase("Menaphos") {
    private val banks = mapOf(Task.Fishing to StaticEntity(107497, 107489, 3217, 2623), Task.Woodcutting to StaticEntity(107487, 3172, 2705))
    private var captureDivineBlessings = false
    private var captureSerenSpirits = false
    private val fish = setOf(40287, 40289, 40291)
    private var fishCaught = 0
    private val fishingBait = 313
    private var initialFishingXp = 0
    private var initialWoodcuttingXp = 0
    private var lastFishCount = 0
    private var lastLogCount = 0
    private val logs = 40285
    private var logsCut = 0
    private var resetCounts = false
    private var setupCalled = false
    private var state = State.Idle
    private var task = Task.None
    private var vip = false
    private val vipArea = Area2di(3180, 2741, 3192, 2755)
    private val vipBank = StaticEntity(107737, 3182, 2741)

    override fun loop() {
        if (!setupCalled) {
            setup()
        }

        if (task == Task.None) {
            state = State.Idle
            return
        }

        val self = Players.self() ?: return

        if (self.isMoving()) {
            return
        }

        if (resetCounts && Inventory.isEmpty()) {
            lastFishCount = 0
            lastLogCount = 0
            resetCounts = false
        }

        // Divine blessings/seren spirits take priority

        if (captureDivineBlessings) {
            Npcs.closest(Filters.by { npc -> npc.getName() == "Divine blessing" })?.interact(Action.Npc1)
        }

        if (captureSerenSpirits) {
            Npcs.closest(Filters.by { npc -> npc.getName() == "Seren spirit" })?.interact(Action.Npc1)
        }

        if (task == Task.Fishing) {
            // Determine how many fish have been caught since last iteration

            val fishCount = Inventory.countMany(fish)

            if (lastFishCount != fishCount) {
                fishCaught += max(0, fishCount - lastFishCount)
                lastFishCount = fishCount
            }
        }

        if (task == Task.Woodcutting) {
            // Determine how many logs have been cut since last iteration

            val logCount = Inventory.count(logs)

            if (lastLogCount != logCount) {
                logsCut += max(0, logCount - lastLogCount)
                lastLogCount = logCount
            }
        }

        if (self.isAnimationPlaying()) {
            return
        }

        if (Inventory.isFull()) {
            bank()
        }

        else {
            if (task == Task.Fishing) {
                fish()
            }

            if (task == Task.Woodcutting) {
                cut()
            }
        }
    }

    override fun paint() {
        val runtime = getRuntime()

        captureDivineBlessings = ImGui.checkbox("Capture divine blessings", captureDivineBlessings)
        captureSerenSpirits = ImGui.checkbox("Capture seren spirits", captureSerenSpirits)
        vip = ImGui.checkbox("Menaphos VIP", vip)

        ImGui.label("Selected task = ${task.text}")
        ImGui.label("State = ${state.text}")
        ImGui.label("Runtime = ${Time.formatTime(runtime)}")

        if (task == Task.Fishing) {
            val xpEarned = Client.getSkillXp(Skill.Fishing) - initialFishingXp

            ImGui.label("Fish caught = $fishCaught")
            ImGui.label("XP earned = $xpEarned (${Time.perHour(runtime, xpEarned)}/h)")
        }

        if (task == Task.Woodcutting) {
            val xpEarned = Client.getSkillXp(Skill.Woodcutting) - initialWoodcuttingXp

            ImGui.label("Logs cut = $logsCut")
            ImGui.label("XP earned = $xpEarned (${Time.perHour(runtime, xpEarned)}/h)")
        }

        task = Task.valueOf(ImGui.intSlider("", task.ordinal, 0, Task.values().size - 1))
    }

    private fun bank() {
        if (state != State.Banking) {
            state = State.Banking
        }

        if (vip) {
            if (Bank.isOpen()) {
                Bank.depositAllExclude(fishingBait)
                resetCounts = true
            }

            else {
                vipBank.interact(Action.Object2)
            }
        }

        else {
            val bank = banks[task] ?: return

            if (SceneObjects.closest(Filters.by { sceneObject -> sceneObject.getName() == "Bank chest" && !vipArea.contains(sceneObject.getGlobalPosition().toVector2i()) }) != null) {
                if (Bank.isOpen()) {
                    Bank.depositAllExclude(fishingBait)
                    resetCounts = true
                }

                else {
                    bank.interactAlternate(Action.Object2)
                }
            }

            else {
                bank.interact(Action.Object2)
                resetCounts = true
            }
        }
    }

    private fun cut() {
        if (state != State.CuttingTree) {
            state = State.CuttingTree
        }

        SceneObjects.closest(Filters.by { sceneObject -> sceneObject.getName() == "Acadia tree" && vip == vipArea.contains(sceneObject.getGlobalPosition().toVector2i()) })?.interact(Action.Object1)
    }

    private fun fish() {
        if (state != State.Fishing) {
            state = State.Fishing
        }

        Npcs.closest(Filters.by { npc -> npc.getName() == "Fishing spot" })?.interact(Action.Npc1)
    }

    private fun setup() {
        initialFishingXp = Client.getSkillXp(Skill.Fishing)
        initialWoodcuttingXp = Client.getSkillXp(Skill.Woodcutting)
        lastFishCount = Inventory.countMany(fish)
        lastLogCount = Inventory.count(logs)
        setupCalled = true
    }
}