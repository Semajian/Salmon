import kraken.plugin.PluginBase
import kraken.plugin.api.*
import shared.Filters
import shared.StaticEntity
import shared.enums.Action
import shared.enums.Skill
import shared.enums.Spirit
import shared.extensions.TypeCasts.toVector2i
import utilities.Time

class Plugin: PluginBase("Menaphos") {
    private val banks = mapOf(PluginTask.Fishing to Pair(107497, 107489), PluginTask.Woodcutting to Pair(107492, 107487))
    private var captureDivineBlessings = false
    private var captureSerenSpirits = false
    private val fish = setOf(40287, 40289, 40291)
    private var fishCaught = 0
    private var initialFishingXp = 0
    private var initialWoodcuttingXp = 0
    private var lastFishCount = 0
    private var lastLogCount = 0
    private var logs = 40285
    private var logsCut = 0
    private var pluginState = PluginState.Idle
    private var pluginTask = PluginTask.None
    private var setupCalled = false
    private var vip = false
    private val vipBank = StaticEntity(Vector2i(3182, 2741), 107737)
    private val vipTreeArea = Area2di(Vector2i(3180, 2747), Vector2i(3192, 2753))

    override fun loop() {
        if (!setupCalled) {
            setup()
        }

        val self = Players.self() ?: return

        if (self.isMoving()) {
            return
        }

        // Divine blessings/seren spirits take priority

        if (captureDivineBlessings && Npcs.tryFindAndInteractSpirit(Spirit.DivineBlessing)) {
            Debug.log("Captured divine blessing")
        }

        if (captureSerenSpirits && Npcs.tryFindAndInteractSpirit(Spirit.SerenSpirit)) {
            Debug.log("Captured seren spirit")
        }

        when (pluginTask) {
            PluginTask.Fishing -> {
                // Determine how many fish have been caught since last iteration

                val fishCount = Inventory.count(fish)

                if (lastFishCount != fishCount) {
                    fishCaught += fishCount - lastFishCount
                    lastFishCount = fishCount
                }
            }
            PluginTask.Woodcutting ->  {
                // Determine how many logs have been cut since last iteration

                val logCount = Inventory.count(logs)

                if (lastLogCount != logCount) {
                    logsCut += logCount - lastLogCount
                    lastLogCount = logCount
                }
            }
            else -> {
                pluginState = PluginState.Idle
            }
        }

        if (self.isAnimationPlaying()) {
            return
        }

        if (Inventory.isFull()) {
            bank()
        }

        else {
            if (pluginTask == PluginTask.Fishing) {
                fish()
            }

            if (pluginTask == PluginTask.Woodcutting) {
                cut()
            }
        }
    }

    override fun paint() {
        val runtime = getRuntime()

        captureDivineBlessings = ImGui.checkbox("Capture divine blessings", captureDivineBlessings)
        captureSerenSpirits = ImGui.checkbox("Capture seren spirits", captureSerenSpirits)
        vip = ImGui.checkbox("Menaphos VIP", vip)

        ImGui.label("Selected task = ${pluginTask.name}")
        ImGui.label("State = ${pluginState.text}")
        ImGui.label("Runtime = ${Time.formatTime(runtime)}")

        if (pluginTask == PluginTask.Fishing) {
            val xpEarned = Client.getSkillXp(Skill.Fishing) - initialFishingXp

            ImGui.label("Fish caught = $fishCaught")
            ImGui.label("XP earned = $xpEarned (${Time.perHour(runtime, xpEarned)}/h)")
        }

        if (pluginTask == PluginTask.Woodcutting) {
            val xpEarned = Client.getSkillXp(Skill.Woodcutting) - initialWoodcuttingXp

            ImGui.label("Logs cut = $logsCut")
            ImGui.label("XP earned = $xpEarned (${Time.perHour(runtime, xpEarned)}/h)")
        }

        pluginTask = PluginTask.valueOf(ImGui.intSlider("", pluginTask.ordinal, 0, PluginTask.values().size - 1))
    }

    override fun paintOverlay() {
        return
    }

    private fun bank() {
        if (pluginState != PluginState.Banking) {
            Debug.log("Inventory full, banking")
            pluginState = PluginState.Banking

            if (pluginTask == PluginTask.Fishing) {
                lastFishCount = 0
            }

            if (pluginTask == PluginTask.Woodcutting) {
                lastLogCount = 0
            }
        }

        if (vip) {
            if (Bank.isOpen()) Bank.depositAll() else vipBank.interact(Action.Object2)
        }

        else {
            val bank = banks[pluginTask] ?: return

            SceneObjects.closest(Filters.bySceneObject { sceneObject -> sceneObject.getId() == bank.first })?.let {
                if (it.getName().equals("Bank chest", true)) {
                    if (Bank.isOpen()) {
                        Bank.depositAll()
                    }

                    else {
                        if (pluginTask == PluginTask.Fishing) {
                            it.interactAlternate(Action.Object2, bank.second)
                        }

                        if (pluginTask == PluginTask.Woodcutting) {
                            it.interact(Action.Object2)
                        }
                    }
                }

                else {
                    // The bank chest hasn't been unlocked and a deposit box is present

                    if (pluginTask == PluginTask.Fishing) {
                        it.interact(Action.Object2)
                    }

                    if (pluginTask == PluginTask.Woodcutting) {
                        it.interactAlternate(Action.Object2, bank.second)
                    }
                }
            }
        }
    }

    private fun cut() {
        if (pluginState == PluginState.CuttingTree) {
            Debug.log("Tree depleted, finding new tree")
        }

        pluginState = PluginState.CuttingTree
        SceneObjects.closest(Filters.bySceneObject { sceneObject -> sceneObject.getName() == "Acadia tree" && vip == vipTreeArea.contains(sceneObject.getGlobalPosition().toVector2i()) })?.interact(Action.Object1)
    }

    private fun fish() {
        if (pluginState == PluginState.Fishing) {
            Debug.log("Fishing spot depleted, finding new spot")
        }

        pluginState = PluginState.Fishing
        Npcs.closest(Filters.byNpc { npc -> npc.getName() == "Fishing spot" })?.interact(Action.Npc1)
    }

    private fun setup() {
        initialFishingXp = Client.getSkillXp(Skill.Fishing)
        initialWoodcuttingXp = Client.getSkillXp(Skill.Woodcutting)
        lastFishCount = Inventory.count(fish)
        lastLogCount = Inventory.count(logs)
        setupCalled = true
    }
}