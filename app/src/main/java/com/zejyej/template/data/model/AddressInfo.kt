package com.zejyej.template.data.model

/**
 * @desc
 * @author zejyej
 * @date 2018/5/7
 */
data class AddressInfo(
        val id:String?,
        val name:String?,
        val children: List<ChildrenCity>?
): IPickerViewData {

    data class ChildrenCity(
            val id: String?,
            val name: String?,
            val children: List<ChildrenArea>?
    ):IPickerViewData {

        data class ChildrenArea(
                val id: String?,
                val name: String?,
                val children: List<ChildrenRoad>?
        ): IPickerViewData {

            data class ChildrenRoad(
                    val id: String?,
                    val name: String?,
                    val children:Any?
            )

            override fun getPickerViewText(): String? {
                return name
            }
        }


        override fun getPickerViewText(): String? {
            return name
        }
    }

    override fun getPickerViewText(): String? {
        return name
    }
}
