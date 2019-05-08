package com.djt.base.utils

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.text.InputType
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.GravityEnum
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.Theme
import com.djt.base.R


/**
 * Created by goldze on 2017/5/10.
 */

class MaterialDialogUtils {

    fun showThemed(context: Context, title: String, content: String) {
        MaterialDialog.Builder(context)
            .title(title)
            .content(content)
            .positiveText("agree")
            .negativeText("disagree")
            .positiveColorRes(R.color.white)
            .negativeColorRes(R.color.white)
            .titleGravity(GravityEnum.CENTER)
            .titleColorRes(R.color.white)
            .contentColorRes(android.R.color.white)
            .backgroundColorRes(R.color.material_blue_grey_800)
            .dividerColorRes(R.color.white)
            .btnSelector(R.drawable.md_selector, DialogAction.POSITIVE)
            .positiveColor(Color.WHITE)
            .negativeColorAttr(android.R.attr.textColorSecondaryInverse)
            .theme(Theme.DARK)
            .autoDismiss(true)  //点击是否关闭对话框
            .showListener {
                //dialog 出现
            }
            .cancelListener {
                //dialog 消失（返回键）
            }
            .dismissListener {
                //dialog 消失
            }
            .show()


    }

    companion object {

        /***
         * 获取一个耗时等待对话框
         *
         * @param horizontal
         * @return MaterialDialog.Builder
         */
        fun showIndeterminateProgressDialog(
            context: Context,
            content: String,
            horizontal: Boolean
        ): MaterialDialog.Builder {
            return MaterialDialog.Builder(context)
                .title(content)
                .progress(true, 0)
                .progressIndeterminateStyle(horizontal)
                .canceledOnTouchOutside(false)
                .backgroundColorRes(R.color.white)
                .keyListener { _, keyCode, event ->
                    if (event.action == KeyEvent.ACTION_DOWN) {//如果是按下，则响应，否则，一次按下会响应两次
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            //activity.onBackPressed();

                        }
                    }
                    false//false允许按返回键取消对话框，true除了调用取消，其他情况下不会取消
                }
        }


        /***
         * 获取基本对话框
         *
         * @param
         * @return MaterialDialog.Builder
         */
        fun showBasicDialog(context: Context, content: String): MaterialDialog.Builder {

//                .stackingBehavior(StackingBehavior.ALWAYS)  //按钮排列方式
            //                .iconRes(R.mipmap.demo_ic_launcher)
            //                .limitIconToDefaultSize() // limits the displayed icon size to 48dp
            //                .onAny(new MaterialDialog.SingleButtonCallback() {
            //                    @Override
            //                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction
            //                            which) {
            //                    }
            //                })
            //                .checkBoxPromptRes(R.string.app_name, false, null)

            return MaterialDialog.Builder(context)
                .title(content)
                .positiveText("确定")
                .negativeText("取消")
        }

        /***
         * 显示一个基础的对话框  只有内容没有标题
         * 吴志杰
         * @param
         * @return MaterialDialog.Builder
         */
        fun showBasicDialogNoTitle(context: Context, content: String): MaterialDialog.Builder {

            return MaterialDialog.Builder(context)
                .content(content)
                .positiveText("确定")
                .negativeText("取消")
        }


        /***
         * 显示一个基础的对话框  带标题 带内容
         * 没有取消按钮
         * 吴志杰
         * @param
         * @return MaterialDialog.Builder
         */
        fun showBasicDialogNoCancel(context: Context, title: String, content: String): MaterialDialog.Builder {

            return MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveText("确定")
        }

        /***
         * 显示一个基础的对话框  带标题 带内容
         * 吴志杰
         * @param
         * @return MaterialDialog.Builder
         */
        fun showBasicDialog(context: Context, title: String, content: String): MaterialDialog.Builder {

            return MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveText("确定")
                .negativeText("取消")
        }

        /***
         * 显示一个基础的对话框  带标题 带内容
         * 吴志杰
         * @return MaterialDialog.Builder
         */
        fun showBasicDialogPositive(context: Context, title: String, content: String): MaterialDialog.Builder {

            return MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveText("复制")
                .negativeText("取消")
        }

        /***
         * 选择图片等Item的对话框  带标题
         * 吴志杰
         * @param
         * @return MaterialDialog.Builder
         */
        fun getSelectDialog(context: Context, title: String, arrays: Array<String>): MaterialDialog.Builder {
            val builder = MaterialDialog.Builder(context)
                .items(arrays.toMutableList())
                .itemsColor(-0xba915a)
                .negativeText("取消")
            if (!TextUtils.isEmpty(title)) {
                builder.title(title)
            }
            return builder
        }

        /***
         * 获取LIST对话框
         *
         * @param
         * @return MaterialDialog.Builder
         */
        fun showBasicListDialog(context: Context, title: String, content: MutableList<Any>): MaterialDialog.Builder {

            return MaterialDialog.Builder(context)
                .title(title)
                .items(content)
                .itemsCallback { dialog, itemView, position, text -> }
                .negativeText("取消")
        }

        /***
         * 获取单选LIST对话框
         *
         * @param
         * @return MaterialDialog.Builder
         */
        fun showSingleListDialog(context: Context, title: String, content: MutableList<Any>): MaterialDialog.Builder {

            return MaterialDialog.Builder(context)
                .title(title)
                .items(content)
                .itemsCallbackSingleChoice(1, object : MaterialDialog.ListCallbackSingleChoice {
                    override fun onSelection(
                        dialog: MaterialDialog, itemView: View, which: Int,
                        text: CharSequence
                    ): Boolean {


                        return true // allow selection
                    }
                })
                .positiveText("选择")
        }


        /***
         * 获取多选LIST对话框
         *
         * @param
         * @return MaterialDialog.Builder
         */
        fun showMultiListDialog(context: Context, title: String, content: MutableList<Any>): MaterialDialog.Builder {

            return MaterialDialog.Builder(context)
                .title(title)
                .items(content)
                .itemsCallbackMultiChoice(arrayOf<Int>(1, 3), object : MaterialDialog.ListCallbackMultiChoice {

                    override fun onSelection(
                        dialog: MaterialDialog,
                        which: Array<Int>,
                        text: Array<CharSequence>
                    ): Boolean {


                        return true
                    }
                })
                .onNeutral(object : MaterialDialog.SingleButtonCallback {
                    override fun onClick(dialog: MaterialDialog, which: DialogAction) {
                        dialog.clearSelectedIndices()
                    }
                })
                .alwaysCallMultiChoiceCallback()
                .positiveText(R.string.md_choose_label)
                .autoDismiss(false)
                .neutralText("clear")
                .itemsDisabledIndices(0, 1)
        }


        /***
         * 获取自定义对话框
         *
         * @param
         * @return MaterialDialog.Builder
         */
        fun showCustomDialog(context: Context, title: String, content: Int) {

            val dialog = MaterialDialog.Builder(context)
                .title(title)
                .customView(content, true)
                .positiveText("确定")
                .negativeText(android.R.string.cancel)
                .onPositive(object : MaterialDialog.SingleButtonCallback {
                    override fun onClick(dialog: MaterialDialog, which: DialogAction) {

                    }
                }).build()
        }


        /***
         * 获取输入对话框
         *
         * @param
         * @return MaterialDialog.Builder
         */
        fun showInputDialog(context: Context, title: String, content: String): MaterialDialog.Builder {

            return MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .inputType(
                    InputType.TYPE_CLASS_TEXT or
                            InputType.TYPE_TEXT_VARIATION_PERSON_NAME or
                            InputType.TYPE_TEXT_FLAG_CAP_WORDS
                )
                .positiveText("确定")
                .negativeText("取消")
                .input("hint", "prefill", true, object : MaterialDialog.InputCallback {
                    override fun onInput(dialog: MaterialDialog, input: CharSequence) {

                    }
                })
        }
    }

}
