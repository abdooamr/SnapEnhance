package me.rhunk.abdooenhance.mapper.impl

import me.rhunk.abdooenhance.mapper.AbstractClassMapper
import me.rhunk.abdooenhance.mapper.ext.findConstString
import me.rhunk.abdooenhance.mapper.ext.getClassName

class ScoreUpdateMapper : AbstractClassMapper("ScoreUpdate") {
    val classReference = classReference("class")

    init {
        mapper {
            for (classDef in classes) {
                val toStringMethod = classDef.methods.firstOrNull {
                    it.name == "toString"
                } ?: continue
                if (classDef.methods.none {
                    it.name == "<init>" &&
                    it.parameterTypes.size > 4
                }) continue

                if (toStringMethod.implementation?.findConstString("selectFriendUserScoresNeedToUpdate", contains = true) != true) continue

                classReference.set(classDef.getClassName())
                return@mapper
            }
        }
    }
}