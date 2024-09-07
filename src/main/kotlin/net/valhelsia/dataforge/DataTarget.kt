package net.valhelsia.dataforge

import net.neoforged.neoforge.data.event.GatherDataEvent

enum class DataTarget(val isEnabled: (GatherDataEvent) -> Boolean) {
    CLIENT({ it.includeClient() }),
    SERVER({ it.includeServer() }),
    DEV({ it.includeDev() }),
    REPORTS({ it.includeReports() }),
}