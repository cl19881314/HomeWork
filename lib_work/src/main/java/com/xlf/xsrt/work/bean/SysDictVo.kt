package com.xlf.xsrt.work.bean

class SysDictVo {
    var sysDictId: Int? = null
    var sysDictName: String? = null
    var subFlag: Int? = null //是否有子集 0 没有 1有
    var subDataList: List<SysDictVo>? = null

}