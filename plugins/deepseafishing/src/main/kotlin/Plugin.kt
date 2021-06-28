import kraken.plugin.PluginBase
import kraken.plugin.api.*
import shared.Filters
import shared.StaticEntity
import shared.enums.Action
import shared.enums.Skill
import utilities.Time
import kotlin.math.max

class Plugin: PluginBase("Deep Sea Fishing") {
    private val banks = mapOf(Task.Jellyfish to StaticEntity(110857, 2117, 7121),
                              Task.Sailfish to StaticEntity(110857, 2117, 7121),
                              Task.Swarm to StaticEntity(110857, 2096, 7089))
    private var captureDivineBlessings = false
    private var captureSerenSpirits = false
    private val fish = setOf(341, 345, 353, 363, 373, 383, 389, 395, 7944, 15264, 15270, 34727, 42249, 42256, 42265)
    private var fishCaught = 0
    private var initialFishingXp = 0
    private var lastFishCount = 0
    private var resetCounts = false
    private var setupCalled = false
    private var state = State.Idle
    private var task = Task.None

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
            resetCounts = false
        }

        // Divine blessings/seren spirits take priority

        if (captureDivineBlessings) {
            Npcs.closest(Filters.by { npc -> npc.getName() == "Divine blessing" })?.interact(Action.Npc1)
        }

        if (captureSerenSpirits) {
            Npcs.closest(Filters.by { npc -> npc.getName() == "Seren spirit" })?.interact(Action.Npc1)
        }

        // Determine how many fish have been caught since last iteration

        val fishCount = Inventory.countMany(fish)

        if (lastFishCount != fishCount) {
            fishCaught += max(0, fishCount - lastFishCount)
            lastFishCount = fishCount
        }

        if (task != Task.Jellyfish && self.isAnimationPlaying()) {
            return
        }

        if (Inventory.isFull()) {
            bank()
        }

        else {
            fish()
        }
    }

    override fun paint() {
        val runtime = getRuntime()
        val xpEarned = Client.getSkillXp(Skill.Fishing) - initialFishingXp

        captureDivineBlessings = ImGui.checkbox("Capture divine blessings", captureDivineBlessings)
        captureSerenSpirits = ImGui.checkbox("Capture seren spirits", captureSerenSpirits)

        ImGui.label("Selected task = ${task.text}")
        ImGui.label("State = ${state.text}")
        ImGui.label("Runtime = ${Time.formatTime(runtime)}")
        ImGui.label("Fish caught = $fishCaught")
        ImGui.label("XP earned = $xpEarned (${Time.perHour(runtime, xpEarned)}/h)")

        task = Task.valueOf(ImGui.intSlider("", task.ordinal, 0, Task.values().size - 1))
    }

    private fun bank() {
        if (state != State.Banking) {
            state = State.Banking
        }

        banks[task]?.interact(Action.Object2)
        resetCounts = true
    }

    private fun fish() {
        if (state != State.Fishing) {
            state = State.Fishing
        }

        if (state == State.Fishing && task == Task.Jellyfish) {
            // Check if jellyfish has turned electrifying

            val self = Players.self() ?: return
            val interactingIndex = self.getInteractingIndex()

            if (self.isAnimationPlaying() && interactingIndex != -1) {
                Npcs.closest(Filters.by { npc -> npc.getServerIndex() == interactingIndex })?.let {
                    if (!it.getName().startsWith("Electrifying")) {
                        return
                    }
                }
            }
        }

        var fishingSpot: Npc? = null

        if (task == Task.Jellyfish) {
            // Prioritise blue blubber jellyfish if possible

            if (Client.getSkillLevel(Skill.Fishing) >= 91) {
                fishingSpot = Npcs.closest(Filters.by { npc -> npc.getName() == "Blue blubber jellyfish" })
            }

            if (fishingSpot == null) {
                fishingSpot = Npcs.closest(Filters.by { npc -> npc.getName() == "Green blubber jellyfish" })
            }
        }

        if (task == Task.Sailfish) {
            // Prioritise calm/swift sailfish if possible

            fishingSpot = Npcs.closest(Filters.by { npc -> npc.getName() == "Calm sailfish" })

            if (fishingSpot == null) {
                fishingSpot = Npcs.closest(Filters.by { npc -> npc.getName() == "Swift sailfish" })
            }

            if (fishingSpot == null) {
                fishingSpot = Npcs.closest(Filters.by { npc -> npc.getName() == "Sailfish" })
            }

        }

        if (task == Task.Swarm) {
            fishingSpot = Npcs.closest(Filters.by { npc -> npc.getName() == "Swarm" })
        }

        fishingSpot?.interact(Action.Npc1)
    }

    private fun setup() {
        initialFishingXp = Client.getSkillXp(Skill.Fishing)
        lastFishCount = Inventory.countMany(fish)
        setupCalled = true
    }
}