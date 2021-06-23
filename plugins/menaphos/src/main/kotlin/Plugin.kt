import kraken.plugin.PluginBase
import kraken.plugin.api.*
import shared.StaticEntity
import shared.enums.Action
import shared.enums.PluginState
import shared.enums.Skill
import shared.filters.NpcFilter
import shared.filters.SceneObjectFilter
import utilities.Time

class Plugin: PluginBase("Menaphos") {
    private val banks = mapOf(PluginTask.Fishing to Pair(107489, 107497), PluginTask.Woodcutting to Pair(107492, 107487))
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
    private var pluginTask = PluginTask.None
    private var setupCalled = false
    private var vip = false
    private val vipBank = StaticEntity(Vector2i(3182, 2741), 107737)

    override fun loop() {
        if (!setupCalled) {
            setup()
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
                return
            }
        }

        // Divine blessings/seren spirits take priority

        Npcs.getClosest("Divine blessing")?.let {
            it.interact(Action.Npc1)
            Debug.log("Captured divine blessing")
        }

        Npcs.getClosest("Seren spirit")?.let {
            it.interact(Action.Npc1)
            Debug.log("Captured seren spirit")
        }

        val self = Players.self() ?: return

        if (self.isAnimationPlaying() || self.isMoving()) {
            return
        }

        if (Inventory.isFull()) {
            bank()
        }

        when (pluginTask) {
            PluginTask.Fishing -> fish()
            PluginTask.Woodcutting -> cut()
            else -> return
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

            if (pluginTask == PluginTask.Fishing) {
                lastFishCount = 0
            }

            if (pluginTask == PluginTask.Woodcutting) {
                lastLogCount = 0
            }
        }

        if (vip) {
            if (Bank.isOpen()) {
                Bank.depositAll()
            }

            else {
                vipBank.interact(Action.Object2)
            }
        }

        else {
            SceneObjects.getClosest(banks[pluginTask]?.first ?: return)?.let {
                if (it.getName().equals("Bank chest", true)) {
                    if (Bank.isOpen()) {
                        Bank.depositAll()
                    }

                    else {
                        it.interact(Action.Object2)
                    }
                }

                else {
                    // The bank chest hasn't been unlocked and a deposit box is present

                    it.interactAlternate(Action.Object2, banks[pluginTask]!!.second)
                }
            }
        }
    }

    private fun cut() {
        if (pluginState == PluginState.CuttingTree) {
            Debug.log("Tree depleted, finding new tree")
        }

        pluginState = PluginState.CuttingTree
        SceneObjects.closest(SceneObjectFilter.byName("Acadia tree"))?.interact(Action.Object1)
    }

    private fun fish() {
        if (pluginState == PluginState.Fishing) {
            Debug.log("Fishing spot depleted, finding new spot")
        }

        pluginState = PluginState.Fishing
        Npcs.closest(NpcFilter.byName("Fishing spot"))?.interact(Action.Npc1)
    }

    private fun setup() {
        initialFishingXp = Client.getSkillXp(Skill.Fishing)
        initialWoodcuttingXp = Client.getSkillXp(Skill.Woodcutting)
        lastFishCount = Inventory.count(fish)
        lastLogCount = Inventory.count(logs)
        setupCalled = true
    }
}