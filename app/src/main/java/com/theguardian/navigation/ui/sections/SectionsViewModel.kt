package com.theguardian.navigation.ui.sections

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theguardian.navigation.ui.content.ScreenLauncher
import java.util.*

class SectionsViewModel : ViewModel(), ScreenLauncher {

    private val mutableTopContent: MutableLiveData<Content> = MutableLiveData()
    val topContent: LiveData<Content> = mutableTopContent

    private val contentStack: Stack<Content> = Stack()

    fun init(content: Content = Content.Front) {
        if (contentStack.isEmpty()) {
            contentStack.push(content)
            updateTopContent()
        }
    }

    override fun openArticle() {
        contentStack.push(Content.Article)
        updateTopContent()
    }

    override fun openList() {
        contentStack.push(Content.List)
        updateTopContent()
    }

    override fun openFront() {
        contentStack.push(Content.Front)
        updateTopContent()
    }

    fun goBack(): Boolean {
        if (contentStack.size == 1) return false
        contentStack.pop()
        updateTopContent()
        return true
    }

    private fun updateTopContent() {
        mutableTopContent.value = contentStack.peek()
    }
}


sealed class Content {
    object Article : Content()
    object List : Content()
    object Front : Content()
}