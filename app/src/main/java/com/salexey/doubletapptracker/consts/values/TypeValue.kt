package com.salexey.doubletapptracker.consts.values

import com.salexey.doubletapptracker.R

enum class TypeValue(
    val strId: Int,
    val value: String
) {
    POSITIVE(strId = R.string.positive, value = "positive"),
    NEGATIVE(strId = R.string.negative, value = "negative");


    //TODO спросить можно ли так делать
    companion object{
        fun getTypeByValue(value: String): TypeValue {

            for (type in TypeValue.values()){
                if (value == type.value){
                    return type
                }
            }

            throw IllegalArgumentException("TypeValue has no value: $value")
        }
    }
}
