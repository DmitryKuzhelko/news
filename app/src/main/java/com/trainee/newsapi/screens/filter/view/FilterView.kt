package com.trainee.newsapi.screens.filter.view

import com.trainee.newsapi.screens.base.view.BaseView

interface FilterView : BaseView {

    //После нажатия кнопки на тулбаре собираются данные из полей сортировки/фильтров, пакуются в интент и отправляются в презентер
    // при этом сама активность закрывается, а результат запроса по параметрам отображается на экране News
    fun checkFilter()

    fun initToolbar()
}