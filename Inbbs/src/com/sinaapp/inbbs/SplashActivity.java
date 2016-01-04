package com.sinaapp.inbbs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

/**
 * 
 * @创建者 xcai
 * @创建时间 2016-1-3 上午10:57:44
 * @描述 引导界面
 * 
 * @版本 $Rev$
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 TODO
 */
public class SplashActivity extends Activity {
	private ImageView mIv_splash;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 初始化控件
		initView();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		setContentView(R.layout.activity_splash);
		mIv_splash = (ImageView) findViewById(R.id.iv_splash);
		startAnim();
	}

	private void startAnim() {
		// 1、缩放 动画
		ScaleAnimation scaleAnimation = new ScaleAnimation(//
				0, 1, 0, 1, //
				ScaleAnimation.RELATIVE_TO_SELF, 0.5f,//
				ScaleAnimation.RELATIVE_TO_SELF, 0.5f);

		// 2、透明度 动画
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);

		AnimationSet animationSet = new AnimationSet(true);

		// 3、将动画添加到动画集中
		// animationSet.addAnimation(rotateAnimation);
		animationSet.addAnimation(scaleAnimation);
		animationSet.addAnimation(alphaAnimation);

		// 4、设置动画时长
		animationSet.setDuration(5000);

		// 5、绑定动画,并开启
		mIv_splash.startAnimation(animationSet);

		// 6、设置动画监听器，当动画播放完后，跳转到主界面
		setOnAnimationSetLinstener(animationSet);

	}

	/**
	 * 设置动画监听器，当动画播放完后，跳转到主界面
	 */
	private void setOnAnimationSetLinstener(AnimationSet animationSet) {
		animationSet.setAnimationListener(new AnimationListener() {
			// 动画开始时，调用
			@Override
			public void onAnimationStart(Animation animation) {

			}

			// 动画重新开始时，调用
			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			// 动画结束时，调用
			@Override
			public void onAnimationEnd(Animation animation) {

				// 执行这，说明没有浏览完引导页，那么当动画播放完后，跳转到引导界面
				Intent intent = new Intent(SplashActivity.this, MainActivity.class);
				startActivity(intent);

				finish();
			}
		});

	}

}
