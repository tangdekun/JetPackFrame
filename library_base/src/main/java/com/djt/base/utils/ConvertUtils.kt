package com.djt.base.utils

import android.annotation.SuppressLint
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import com.djt.base.utils.StringUtils.Companion.isSpace
import com.djt.base.utils.constant.MemoryConstants
import com.djt.base.utils.constant.TimeConstants

import java.io.*
import java.nio.charset.Charset
import kotlin.experimental.and
import kotlin.experimental.or

/**
 * Created by goldze on 2017/5/14.
 * 转换相关工具类
 */
class ConvertUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    /**
     * outputStream转inputStream
     *
     * @param out 输出流
     * @return inputStream子类
     */
    fun output2InputStream(out: OutputStream?): ByteArrayInputStream? {
        return if (out == null) null else ByteArrayInputStream((out as ByteArrayOutputStream).toByteArray())
    }

    companion object {

        private val hexDigits =
            charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')

        /**
         * byteArr转hexString
         *
         * 例如：
         * bytes2HexString(new byte[] { 0, (byte) 0xa8 }) returns 00A8
         *
         * @param bytes 字节数组
         * @return 16进制大写字符串
         */
        fun bytes2HexString(bytes: ByteArray?): String? {
            if (bytes == null) return null
            val len = bytes.size
            if (len <= 0) return null
            val ret = CharArray(len shl 1)
            var i = 0
            var j = 0
            while (i < len) {
                ret[j++] = hexDigits[bytes[i].toInt() ushr 4 and 0x0f]
                ret[j++] = hexDigits[(bytes[i] and 0x0f.toByte()).toInt()]
                i++
            }
            return String(ret)
        }

        /**
         * hexString转byteArr
         *
         * 例如：
         * hexString2Bytes("00A8") returns { 0, (byte) 0xA8 }
         *
         * @param hexString 十六进制字符串
         * @return 字节数组
         */
        fun hexString2Bytes(hexString: String): ByteArray? {
            var hexString = hexString
            if (isSpace(hexString)) return null
            var len = hexString.length
            if (len % 2 != 0) {
                hexString = "0$hexString"
                len += 1
            }
            val hexBytes = hexString.toUpperCase().toCharArray()
            val ret = ByteArray(len shr 1)
            var i = 0
            while (i < len) {
                ret[i shr 1] = (hex2Dec(hexBytes[i]) shl 4 or hex2Dec(hexBytes[i + 1])).toByte()
                i += 2
            }
            return ret
        }

        /**
         * hexChar转int
         *
         * @param hexChar hex单个字节
         * @return 0..15
         */
        private fun hex2Dec(hexChar: Char): Int {
            return if (hexChar >= '0' && hexChar <= '9') {
                hexChar - '0'
            } else if (hexChar >= 'A' && hexChar <= 'F') {
                hexChar - 'A' + 10
            } else {
                throw IllegalArgumentException()
            }
        }

        /**
         * charArr转byteArr
         *
         * @param chars 字符数组
         * @return 字节数组
         */
        fun chars2Bytes(chars: CharArray?): ByteArray? {
            if (chars == null || chars.size <= 0) return null
            val len = chars.size
            val bytes = ByteArray(len)
            for (i in 0 until len) {
                bytes[i] = chars[i].toByte()
            }
            return bytes
        }

        /**
         * byteArr转charArr
         *
         * @param bytes 字节数组
         * @return 字符数组
         */
        fun bytes2Chars(bytes: ByteArray?): CharArray? {
            if (bytes == null) return null
            val len = bytes.size
            if (len <= 0) return null
            val chars = CharArray(len)
            for (i in 0 until len) {
                chars[i] = (bytes[i] and 0xff.toByte()).toChar()
            }
            return chars
        }

        /**
         * 以unit为单位的内存大小转字节数
         *
         * @param memorySize 大小
         * @param unit 单位类型
         *
         *  * [MemoryConstants.BYTE]: 字节
         *  * [MemoryConstants.KB]  : 千字节
         *  * [MemoryConstants.MB]  : 兆
         *  * [MemoryConstants.GB]  : GB
         *
         * @return 字节数
         */
        fun memorySize2Byte(memorySize: Long, @MemoryConstants.Unit unit: Int): Long {
            return if (memorySize < 0) -1 else memorySize * unit
        }

        /**
         * 字节数转以unit为单位的内存大小
         *
         * @param byteNum 字节数
         * @param unit 单位类型
         *
         *  * [MemoryConstants.BYTE]: 字节
         *  * [MemoryConstants.KB]  : 千字节
         *  * [MemoryConstants.MB]  : 兆
         *  * [MemoryConstants.GB]  : GB
         *
         * @return 以unit为单位的size
         */
        fun byte2MemorySize(byteNum: Long, @MemoryConstants.Unit unit: Int): Double {
            return if (byteNum < 0) -1.0 else byteNum.toDouble() / unit
        }

        /**
         * 字节数转合适内存大小
         *
         * 保留3位小数
         *
         * @param byteNum 字节数
         * @return 合适内存大小
         */
        @SuppressLint("DefaultLocale")
        fun byte2FitMemorySize(byteNum: Long): String {
            return if (byteNum < 0) {
                "shouldn't be less than zero!"
            } else if (byteNum < MemoryConstants.KB) {
                String.format("%.3fB", byteNum.toDouble() + 0.0005)
            } else if (byteNum < MemoryConstants.MB) {
                String.format("%.3fKB", byteNum.toDouble() / MemoryConstants.KB + 0.0005)
            } else if (byteNum < MemoryConstants.GB) {
                String.format("%.3fMB", byteNum.toDouble() / MemoryConstants.MB + 0.0005)
            } else {
                String.format("%.3fGB", byteNum.toDouble() / MemoryConstants.GB + 0.0005)
            }
        }

        /**
         * 以unit为单位的时间长度转毫秒时间戳
         *
         * @param timeSpan 毫秒时间戳
         * @param unit 单位类型
         *
         *  * [TimeConstants.MSEC]: 毫秒
         *  * [TimeConstants.SEC]: 秒
         *  * [TimeConstants.MIN]: 分
         *  * [TimeConstants.HOUR]: 小时
         *  * [TimeConstants.DAY]: 天
         *
         * @return 毫秒时间戳
         */
        fun timeSpan2Millis(timeSpan: Long, @TimeConstants.Unit unit: Int): Long {
            return timeSpan * unit
        }

        /**
         * 毫秒时间戳转以unit为单位的时间长度
         *
         * @param millis 毫秒时间戳
         * @param unit 单位类型
         *
         *  * [TimeConstants.MSEC]: 毫秒
         *  * [TimeConstants.SEC]: 秒
         *  * [TimeConstants.MIN]: 分
         *  * [TimeConstants.HOUR]: 小时
         *  * [TimeConstants.DAY]: 天
         *
         * @return 以unit为单位的时间长度
         */
        fun millis2TimeSpan(millis: Long, @TimeConstants.Unit unit: Int): Long {
            return millis / unit
        }

        /**
         * 毫秒时间戳转合适时间长度
         *
         * @param millis 毫秒时间戳
         *
         * 小于等于0，返回null
         * @param precision 精度
         *
         *  * precision = 0，返回null
         *  * precision = 1，返回天
         *  * precision = 2，返回天和小时
         *  * precision = 3，返回天、小时和分钟
         *  * precision = 4，返回天、小时、分钟和秒
         *  * precision &gt;= 5，返回天、小时、分钟、秒和毫秒
         *
         * @return 合适时间长度
         */
        @SuppressLint("DefaultLocale")
        fun millis2FitTimeSpan(millis: Long, precision: Int): String? {
            var millis = millis
            var precision = precision
            if (millis <= 0 || precision <= 0) return null
            val sb = StringBuilder()
            val units = arrayOf("天", "小时", "分钟", "秒", "毫秒")
            val unitLen = intArrayOf(86400000, 3600000, 60000, 1000, 1)
            precision = Math.min(precision, 5)
            for (i in 0 until precision) {
                if (millis >= unitLen[i]) {
                    val mode = millis / unitLen[i]
                    millis -= mode * unitLen[i]
                    sb.append(mode).append(units[i])
                }
            }
            return sb.toString()
        }

        /**
         * bytes转bits
         *
         * @param bytes 字节数组
         * @return bits
         */
        fun bytes2Bits(bytes: ByteArray): String {
            val sb = StringBuilder()
            for (aByte in bytes) {
                for (j in 7 downTo 0) {
                    sb.append(if ((aByte.toInt() shr j) and 0x01 == 0) '0' else '1')
                }
            }
            return sb.toString()
        }

        /**
         * bits转bytes
         *
         * @param bits 二进制
         * @return bytes
         */
        fun bits2Bytes(bits: String): ByteArray {
            var bits = bits
            val lenMod = bits.length % 8
            var byteLen = bits.length / 8
            // 不是8的倍数前面补0
            if (lenMod != 0) {
                for (i in lenMod..7) {
                    bits = "0$bits"
                }
                byteLen++
            }
            val bytes = ByteArray(byteLen)
            for (i in 0 until byteLen) {
                for (j in 0..7) {
                    bytes[i] = (bytes[i].toInt() shl 1).toByte()
                    bytes[i] = bytes[i] or bits[((i * 8 + j).toChar() - '0')].toByte()
                }
            }
            return bytes
        }

        /**
         * inputStream转outputStream
         *
         * @param is 输入流
         * @return outputStream子类
         */
        fun input2OutputStream(input: InputStream?): ByteArrayOutputStream? {
            if (input == null) return null
            try {
                val os = ByteArrayOutputStream()
                val b = ByteArray(MemoryConstants.KB)
                var len: Int
                while ((input?.read(b, 0, MemoryConstants.KB).also { len = it }) != -1) {
                    os.write(b, 0, len)
                }
                return os
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            } finally {
                CloseUtils.closeIO(input)
            }
        }

        /**
         * inputStream转byteArr
         *
         * @param is 输入流
         * @return 字节数组
         */
        fun inputStream2Bytes(input: InputStream?): ByteArray? {
            return if (input == null) null else input2OutputStream(input)!!.toByteArray()
        }

        /**
         * byteArr转inputStream
         *
         * @param bytes 字节数组
         * @return 输入流
         */
        fun bytes2InputStream(bytes: ByteArray?): InputStream? {
            return if (bytes == null || bytes.size <= 0) null else ByteArrayInputStream(bytes)
        }

        /**
         * outputStream转byteArr
         *
         * @param out 输出流
         * @return 字节数组
         */
        fun outputStream2Bytes(out: OutputStream?): ByteArray? {
            return if (out == null) null else (out as ByteArrayOutputStream).toByteArray()
        }

        /**
         * outputStream转byteArr
         *
         * @param bytes 字节数组
         * @return 字节数组
         */
        fun bytes2OutputStream(bytes: ByteArray?): OutputStream? {
            if (bytes == null || bytes.size <= 0) return null
            var os: ByteArrayOutputStream? = null
            try {
                os = ByteArrayOutputStream()
                os!!.write(bytes)
                return os
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            } finally {
                if (os is Closeable) {
                    CloseUtils.closeIO(os)
                }

            }
        }

        /**
         * inputStream转string按编码
         *
         * @param is 输入流
         * @param charsetName 编码格式
         * @return 字符串
         */
        fun inputStream2String(input: InputStream?, charsetName: String): String? {
            if (input == null || isSpace(charsetName)) return null
            return try {
                inputStream2Bytes(input)?.let { String(it, Charset.forName(charsetName)) }
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
                null
            }

        }

        /**
         * string转inputStream按编码
         *
         * @param string 字符串
         * @param charsetName 编码格式
         * @return 输入流
         */
        fun string2InputStream(string: String?, charsetName: String): InputStream? {
            if (string == null || isSpace(charsetName)) return null
            try {
                return ByteArrayInputStream(charsetName.toByteArray())
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
                return null
            }

        }

        /**
         * outputStream转string按编码
         *
         * @param out 输出流
         * @param charsetName 编码格式
         * @return 字符串
         */
        fun outputStream2String(out: OutputStream?, charsetName: String): String? {
            if (out == null || isSpace(charsetName)) return null
            try {
                return outputStream2Bytes(out)?.let { String(it, Charset.forName(charsetName)) }
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
                return null
            }

        }

        /**
         * string转outputStream按编码
         *
         * @param string 字符串
         * @param charsetName 编码格式
         * @return 输入流
         */
        fun string2OutputStream(string: String?, charsetName: String): OutputStream? {
            if (string == null || isSpace(charsetName)) return null
            try {
                return bytes2OutputStream(charsetName.toByteArray())
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
                return null
            }

        }

        /**
         * bitmap转byteArr
         *
         * @param bitmap bitmap对象
         * @param format 格式
         * @return 字节数组
         */
        fun bitmap2Bytes(bitmap: Bitmap?, format: Bitmap.CompressFormat): ByteArray? {
            if (bitmap == null) return null
            val baos = ByteArrayOutputStream()
            bitmap!!.compress(format, 100, baos)
            return baos.toByteArray()
        }

        /**
         * byteArr转bitmap
         *
         * @param bytes 字节数组
         * @return bitmap
         */
        fun bytes2Bitmap(bytes: ByteArray?): Bitmap? {
            return if (bytes == null || bytes.size == 0) null else BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }

        /**
         * drawable转bitmap
         *
         * @param drawable drawable对象
         * @return bitmap
         */
        fun drawable2Bitmap(drawable: Drawable): Bitmap {
            if (drawable is BitmapDrawable) {
                val bitmapDrawable = drawable as BitmapDrawable
                if (bitmapDrawable.getBitmap() != null) {
                    return bitmapDrawable.getBitmap()
                }
            }
            val bitmap: Bitmap
            if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
                bitmap = Bitmap.createBitmap(
                    1, 1,
                    if (drawable.getOpacity() !== PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
                )
            } else {
                bitmap = Bitmap.createBitmap(
                    drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                    if (drawable.getOpacity() !== PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
                )
            }
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
            drawable.draw(canvas)
            return bitmap
        }

        /**
         * bitmap转drawable
         *
         * @param bitmap bitmap对象
         * @return drawable
         */
        fun bitmap2Drawable(bitmap: Bitmap?): Drawable? {
            return if (bitmap == null) null else BitmapDrawable(Utils.getContext().getResources(), bitmap)
        }

        /**
         * drawable转byteArr
         *
         * @param drawable drawable对象
         * @param format 格式
         * @return 字节数组
         */
        fun drawable2Bytes(drawable: Drawable?, format: Bitmap.CompressFormat): ByteArray? {
            return if (drawable == null) null else bitmap2Bytes(drawable2Bitmap(drawable), format)
        }

        /**
         * byteArr转drawable
         *
         * @param bytes 字节数组
         * @return drawable
         */
        fun bytes2Drawable(bytes: ByteArray?): Drawable? {
            return if (bytes == null) null else bitmap2Drawable(bytes2Bitmap(bytes))
        }

        /**
         * view转Bitmap
         *
         * @param view 视图
         * @return bitmap
         */
        fun view2Bitmap(view: View?): Bitmap? {
            if (view == null) return null
            val ret = Bitmap.createBitmap(view!!.getWidth(), view!!.getHeight(), Bitmap.Config.ARGB_8888)
            val canvas = Canvas(ret)
            val bgDrawable = view!!.getBackground()
            if (bgDrawable != null) {
                bgDrawable!!.draw(canvas)
            } else {
                canvas.drawColor(Color.WHITE)
            }
            view!!.draw(canvas)
            return ret
        }

        /**
         * dp转px
         *
         * @param dpValue dp值
         * @return px值
         */
        fun dp2px(dpValue: Float): Int {
            val scale = Utils.getContext().getResources().getDisplayMetrics().density
            return (dpValue * scale + 0.5f).toInt()
        }

        /**
         * px转dp
         *
         * @param pxValue px值
         * @return dp值
         */
        fun px2dp(pxValue: Float): Int {
            val scale = Utils.getContext().getResources().getDisplayMetrics().density
            return (pxValue / scale + 0.5f).toInt()
        }

        /**
         * sp转px
         *
         * @param spValue sp值
         * @return px值
         */
        fun sp2px(spValue: Float): Int {
            val fontScale = Utils.getContext().getResources().getDisplayMetrics().scaledDensity
            return (spValue * fontScale + 0.5f).toInt()
        }

        /**
         * px转sp
         *
         * @param pxValue px值
         * @return sp值
         */
        fun px2sp(pxValue: Float): Int {
            val fontScale = Utils.getContext().getResources().getDisplayMetrics().scaledDensity
            return (pxValue / fontScale + 0.5f).toInt()
        }

        /**
         * 判断字符串是否为null或全为空白字符
         *
         * @param s 待校验字符串
         * @return `true`: null或全空白字符<br></br> `false`: 不为null且不全空白字符
         */
        private fun isSpace(s: String?): Boolean {
            if (s == null) return true
            var i = 0
            val len = s.length
            while (i < len) {
                if (!Character.isWhitespace(s[i])) {
                    return false
                }
                ++i
            }
            return true
        }
    }
}
