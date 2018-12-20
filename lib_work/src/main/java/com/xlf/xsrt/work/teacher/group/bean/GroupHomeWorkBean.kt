package com.xlf.xsrt.work.teacher.group.bean

import com.xlf.xsrt.work.bean.BaseEntry
import com.xlf.xsrt.work.bean.HomeworkBaseVo

/**
 * 已选作业返回bean
 */
class GroupHomeWorkBean : BaseEntry() {
    var groupedCount = -1
    var homeworkName = ""
    var groupedhomeworkList: MutableList<HomeworkBaseVo>? = null
}