package com.ahu.ahutong.data.model

data class Rubbish(val name: String,
                   val type: Int,
                   val aipre: Int,
                   val explain: String,
                   val contain: String,
                   val tip: String){

    fun getTypeStr(): String{
        return typeMap[type] ?: ""
    }
    companion object{
        val typeMap by lazy {
            mapOf(
                0 to "可回收垃圾",
                1 to "有害垃圾",
                2 to "厨余垃圾",
                3 to "其他垃圾"
                )
        }
        val types by lazy {
            listOf(
                Rubbish("", 0, 0,
                    "可回收垃圾是指适宜回收、可循环利用的生活废弃物。",
                    "各类废金属、玻璃瓶、易拉罐、饮料瓶、塑料玩具、书本、报纸、广告单、纸板箱、衣服、床上用品、电子产品等",
                    "轻投轻放；清洁干燥，避免污染，费纸尽量平整；立体包装物请清空内容物，清洁后压扁投放；有尖锐边角的、应包裹后投放"),
                Rubbish("", 1, 0,
                    "有毒有害垃圾是指存有对人体健康有害的重金属、有毒的物质或者对环境造成现实危害或者潜在危害的废弃物。",
                    "废电池、废荧光灯管、废灯泡、废水银温度计、废油漆桶、过期药品、农药、杀虫剂等。",
                    "分类投放有害垃圾时，应注意轻放。其中：废灯管等易破损的有害垃圾应连带包装或包裹后投放；废弃药品宜连带包装一并投放；杀虫剂等压力罐装容器，应排空内容物后投放；在公共场所产生有害垃圾且未发现对应收集容器时，应携带至有害垃圾投放点妥善投放。"),
                Rubbish("", 2, 0,
                    "厨余垃圾是指居民日常生活及食品加工、饮食服务、单位供餐等活动中产生的垃圾。",
                    "菜叶、剩菜、剩饭、果皮、蛋壳、茶渣、骨头等",
                    "纯流质的食物垃圾、如牛奶等，应直接倒进下水口。有包装物的湿垃圾应将包装物去除后分类投放、包装物请投放到对应的可回收物或干垃圾容器"),
                Rubbish("", 3, 0,
                    "干垃圾即其它垃圾，指除可回收物、有害垃圾、厨余垃圾（湿垃圾）以外的其它生活废弃物。",
                    "砖瓦陶瓷、渣土、卫生间废纸、猫砂、污损塑料、毛发、硬壳、一次性制品、灰土、瓷器碎片等难以回收的废弃物",
                    "尽量沥干水分；难以辨识类别的生活垃圾都可以投入干垃圾容器内"),
            )
        }
    }
}
