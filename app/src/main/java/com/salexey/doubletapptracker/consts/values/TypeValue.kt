package com.salexey.doubletapptracker.consts.values

import com.salexey.doubletapptracker.R

enum class TypeValue(
    val strId: Int,
    val value: Int
) {
    NEGATIVE(strId = R.string.negative, value = 0),
    POSITIVE(strId = R.string.positive, value = 1);


    companion object{
        fun getTypeByValue(value: Int): TypeValue {

            for (type in TypeValue.values()){
                if (value == type.value){
                    return type
                }
            }

            throw IllegalArgumentException("TypeValue has no value: $value")
        }
    }
}
