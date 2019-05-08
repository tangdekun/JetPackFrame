package com.djt.base.utils

import android.annotation.TargetApi
import android.os.Build
import android.os.Environment
import android.os.StatFs
import java.io.*

/**
 * Created by goldze on 2017/5/14.
 * SD卡相关工具类
 */
class SDCardUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    class SDCardInfo {
        internal var isExist: Boolean = false
        internal var totalBlocks: Long = 0
        internal var freeBlocks: Long = 0
        internal var availableBlocks: Long = 0
        internal var blockByteSize: Long = 0
        internal var totalBytes: Long = 0
        internal var freeBytes: Long = 0
        internal var availableBytes: Long = 0

        override fun toString(): String {
            return "isExist=" + isExist +
                    "\ntotalBlocks=" + totalBlocks +
                    "\nfreeBlocks=" + freeBlocks +
                    "\navailableBlocks=" + availableBlocks +
                    "\nblockByteSize=" + blockByteSize +
                    "\ntotalBytes=" + totalBytes +
                    "\nfreeBytes=" + freeBytes +
                    "\navailableBytes=" + availableBytes
        }
    }

    companion object {

        /**
         * 判断SD卡是否可用
         *
         * @return true : 可用<br></br>false : 不可用
         */
        val isSDCardEnable: Boolean
            get() = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())

        /**
         * 获取SD卡路径
         *
         * 先用shell，shell失败再普通方法获取，一般是/storage/emulated/0/
         *
         * @return SD卡路径
         */
        val sdCardPath: String?
            get() {
                if (!isSDCardEnable) return null
                val cmd = "cat /proc/mounts"
                val run = Runtime.getRuntime()
                var bufferedReader: BufferedReader? = null
                try {
                    val p = run.exec(cmd)
                    bufferedReader = BufferedReader(InputStreamReader(BufferedInputStream(p.getInputStream())))
                    var lineStr: String
                    while ((bufferedReader!!.readLine().also { lineStr = it }) != null) {
                        if (lineStr.contains("sdcard") && lineStr.contains(".android_secure")) {
                            val strArray = lineStr.split(" ")
                            if (strArray.size >= 5) {
                                return strArray[1].replace("/.android_secure", "") + File.separator
                            }
                        }
                        if (p.waitFor() !== 0 && p.exitValue() === 1) {
                            break
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    if(bufferedReader is Closeable){
                        CloseUtils.closeIO(bufferedReader)
                    }


                }
                return Environment.getExternalStorageDirectory().getPath() + File.separator
            }

        /**
         * 获取SD卡data路径
         *
         * @return SD卡data路径
         */
        val dataPath: String?
            get() = if (!isSDCardEnable) null else Environment.getExternalStorageDirectory().getPath() + File.separator + "data" + File.separator

        /**
         * 获取SD卡剩余空间
         *
         * @return SD卡剩余空间
         */
        val freeSpace: String?
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            get() {
                if (!isSDCardEnable) return null
                val stat = StatFs(sdCardPath)
                val blockSize: Long
                val availableBlocks: Long
                availableBlocks = stat.getAvailableBlocksLong()
                blockSize = stat.getBlockSizeLong()
                return ConvertUtils.byte2FitMemorySize(availableBlocks * blockSize)
            }

        /**
         * 获取SD卡信息
         *
         * @return SDCardInfo
         */
        val sdCardInfo: String?
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            get() {
                if (!isSDCardEnable) return null
                val sd = SDCardInfo()
                sd.isExist = true
                val sf = StatFs(Environment.getExternalStorageDirectory().getPath())
                sd.totalBlocks = sf.getBlockCountLong()
                sd.blockByteSize = sf.getBlockSizeLong()
                sd.availableBlocks = sf.getAvailableBlocksLong()
                sd.availableBytes = sf.getAvailableBytes()
                sd.freeBlocks = sf.getFreeBlocksLong()
                sd.freeBytes = sf.getFreeBytes()
                sd.totalBytes = sf.getTotalBytes()
                return sd.toString()
            }
    }
}
