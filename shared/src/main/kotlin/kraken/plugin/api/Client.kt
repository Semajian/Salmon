package kraken.plugin.api

import shared.enums.Skill

object Client {
    @JvmStatic
    external fun getConVarById(id: Int): ConVar?
    @JvmStatic
    external fun getCurrentHealth(): Int
    @JvmStatic
    external fun getCurrentPrayer(): Int
    @JvmStatic
    external fun getMaxHealth(): Int
    @JvmStatic
    external fun getMaxPrayer(): Int
    @JvmStatic
    external fun getStatById(id: Int): Stat?
    @JvmStatic
    external fun getState(): Int
    @JvmStatic
    external fun isLoading(): Boolean
    @JvmStatic
    external fun worldToScreen(vec: Vector3): Vector2i?

    fun getSkillLevel(skill: Skill): Int {
        return getStatById(skill.id)?.current ?: 0
    }

    fun getSkillXp(skill: Skill): Int {
        return getStatById(skill.id)?.xp ?: 0
    }
}