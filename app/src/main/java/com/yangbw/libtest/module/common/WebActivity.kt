package com.yangbw.libtest.module.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import com.library.common.base.BaseActivity
import com.yangbw.libtest.R
import com.yangbw.libtest.common.CommonViewModel
import com.yangbw.libtest.databinding.ActivityDetailsBinding
import kotlinx.android.synthetic.main.activity_web.*
import kotlinx.android.synthetic.main.toolbar.*
import utils.ActionBarUtils

/**
 * 通用webView
 *
 * @author yangbw
 * @date 2020/9/1
 */
class WebActivity : BaseActivity<CommonViewModel, ActivityDetailsBinding>() {

    private var mAgentWeb: AgentWeb? = null
    private var mTitle: String? = null
    private var mUrl: String? = null

    companion object {
        private const val TITLE = "title"
        private const val URL = "url"

        fun launch(context: Context, title: String?, url: String) {
            context.startActivity(Intent().apply {
                putExtra(TITLE, title)
                putExtra(URL, url)
                setClass(context, WebActivity::class.java)
            })
        }
    }

    override fun getLayoutId() = R.layout.activity_web

    override fun getReplaceView(): View = ll_content

    override fun init(savedInstanceState: Bundle?) {
        mTitle = intent.getStringExtra(TITLE)
        mUrl = intent.getStringExtra(URL)
        mTitle?.let {
            ActionBarUtils.setCenterTitleText(toolbar, it)
        }
        ActionBarUtils.setSupportActionBarWithBack(toolbar) {
            finish()
        }
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(ll_content!!, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .setWebChromeClient(mWebChromeClient)
            .setWebViewClient(mWebViewClient)
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
            .createAgentWeb()
            .ready()
            .go(mUrl)
    }

    private val mWebViewClient: WebViewClient = object : WebViewClient() {
        @Suppress("RedundantOverride")
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }
    }

    private val mWebChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            if (TextUtils.isEmpty(mTitle)) {
                ActionBarUtils.setCenterTitleText(toolbar, title)
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (mAgentWeb!!.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        mAgentWeb!!.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb!!.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb!!.webLifeCycle.onDestroy()
        super.onDestroy()
    }
}
