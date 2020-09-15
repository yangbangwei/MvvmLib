package com.yangbw.libtest.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import com.library.common.extension.gone
import com.library.common.extension.visible
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView
import com.yangbw.libtest.R

/**
 * 视频播放器
 *
 * @author :yangbw
 * @date :2020/9/15
 */
class MVideoPlayer : StandardGSYVideoPlayer {

    private var initFirstLoad = true

    constructor(context: Context) : super(context)

    constructor(context: Context, fullFlag: Boolean?) : super(context, fullFlag)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun getLayoutId() = R.layout.layout_video_player

    override fun updateStartImage() {
        if (mStartButton is ImageView) {
            val imageView = mStartButton as ImageView
            when (mCurrentState) {
                GSYVideoView.CURRENT_STATE_PLAYING -> imageView.setImageResource(R.drawable.ic_pause_white_24dp)
                GSYVideoView.CURRENT_STATE_ERROR -> imageView.setImageResource(R.drawable.ic_play_white_24dp)
                GSYVideoView.CURRENT_STATE_AUTO_COMPLETE -> imageView.setImageResource(R.drawable.ic_refresh_white_24dp)
                else -> imageView.setImageResource(R.drawable.ic_play_white_24dp)
            }

        } else {
            super.updateStartImage()
        }
    }

    //正常
    override fun changeUiToNormal() {
        super.changeUiToNormal()
        initFirstLoad = true
    }

    //准备中
    override fun changeUiToPreparingShow() {
        super.changeUiToPreparingShow()
        mBottomContainer.gone()
    }

    //播放中
    override fun changeUiToPlayingShow() {
        super.changeUiToPlayingShow()
        if (initFirstLoad) mBottomContainer.gone()
        initFirstLoad = false
    }

    //开始缓冲
    override fun changeUiToPlayingBufferingShow() {
        super.changeUiToPlayingBufferingShow()
        if (initFirstLoad) {
            mStartButton.gone()
            initFirstLoad = false
        } else {
            mStartButton.visible()
        }
    }

    //暂停
    override fun changeUiToPauseShow() {
        super.changeUiToPauseShow()
    }

    //自动播放结束
    override fun changeUiToCompleteShow() {
        super.changeUiToCompleteShow()
        mBottomContainer.gone()
    }

    //错误状态
    override fun changeUiToError() {
        super.changeUiToError()
    }

    fun getBottomContainer() = mBottomContainer
}