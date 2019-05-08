package com.djt.base

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import java.lang.ref.WeakReference


/**
 * 盛装Fragment的一个容器(代理)Activity
 * 普通界面只需要编写Fragment,使用此Activity盛装,这样就不需要每个界面都在AndroidManifest中注册一遍
 */
class ContainerActivity : RxAppCompatActivity() {
    protected lateinit var mFragment: WeakReference<Fragment>

    protected override fun onCreate(savedInstanceState: Bundle?) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
        val fm = getSupportFragmentManager()
        var fragment: Fragment? = null
        if (savedInstanceState != null) {
            fragment = fm.getFragment(savedInstanceState, FRAGMENT_TAG)
        }
        if (fragment == null) {
            fragment = initFromIntent(getIntent())
        }
        val trans = getSupportFragmentManager()
            .beginTransaction()
        trans.replace(R.id.content, fragment)
        trans.commitAllowingStateLoss()
        mFragment = WeakReference(fragment)
    }

    protected override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mFragment?.get()?.let { supportFragmentManager.putFragment(outState, FRAGMENT_TAG, it) }
    }

    protected fun initFromIntent(data: Intent?): Fragment {
        if (data == null) {
            throw RuntimeException(
                "you must provide a page info to display"
            )
        }
        try {
            val fragmentName = data!!.getStringExtra(FRAGMENT)
            if (fragmentName == null || "".equals(fragmentName)) {
                throw IllegalArgumentException("can not find page fragmentName")
            }
            val fragmentClass = Class.forName(fragmentName)
            val fragment = fragmentClass.newInstance() as Fragment
            val args = data!!.getBundleExtra(BUNDLE)
            if (args != null) {
                fragment.setArguments(args)
            }
            return fragment
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        throw RuntimeException("fragment initialization failed!")
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    companion object {
        private val FRAGMENT_TAG = "content_fragment_tag"
        val FRAGMENT = "fragment"
        val BUNDLE = "bundle"
    }
}
