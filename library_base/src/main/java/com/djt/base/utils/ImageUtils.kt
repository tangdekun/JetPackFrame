package com.djt.base.utils

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.*
import android.graphics.Bitmap.CompressFormat
import android.graphics.Bitmap.Config
import android.graphics.PorterDuff.Mode
import android.graphics.Shader.TileMode
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.MediaColumns
import android.text.TextUtils
import android.util.DisplayMetrics
import com.djt.base.utils.Utils.Companion.getContext
import com.djt.base.utils.compression.Luban
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

import java.io.*
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


/**
 * Created by goldze on 2017/7/17.
 * 图片相关工具类,包含图片压缩,图片缩放,图片裁剪等功能
 */
object ImageUtils {

    private val MAX_SIZE = 200f//接受的最大图片尺寸为200k，200k以上的图片压缩到200k一下

    val SDCARD_MNT = "/mnt/sdcard"
    val SDCARD = "/sdcard"

    /** 请求相册  */
    val REQUEST_CODE_GETIMAGE_BYSDCARD = 0

    val REQUEST_CODE_GETIMAGE_BYSDCARD_info = 4

    /** 请求相机  */
    val REQUEST_CODE_GETIMAGE_BYCAMERA = 1
    /** 请求裁剪  */
    val REQUEST_CODE_GETIMAGE_BYCROP = 2
    /** 从图片浏览界面发送动弹  */
    val REQUEST_CODE_GETIMAGE_IMAGEPAVER = 3

    /**
     * 使用当前时间戳拼接一个唯一的文件名
     *
     * @param
     * @return
     */
    val tempFileName: String
        get() {
            val format = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_SS")
            return format.format(
                Timestamp(
                    System
                        .currentTimeMillis()
                )
            )
        }

    /**
     * 获取照相机使用的目录
     *
     * @return
     */
    val camerPath: String
        get() = " ${Environment.getExternalStorageDirectory()}+ ${File.separator}+FounderNews +${File.separator}"

    internal var bitmap: Bitmap? = null

    @Throws(IOException::class)
    @JvmOverloads
    fun saveImage(
        context: Context?, fileName: String?,
        bitmap: Bitmap?, quality: Int = 100
    ) {
        if (bitmap == null || fileName == null || context == null)
            return

        val fos = context.openFileOutput(
            fileName,
            Context.MODE_PRIVATE
        )
        val stream = ByteArrayOutputStream()
        bitmap!!.compress(CompressFormat.JPEG, quality, stream)
        val bytes = stream.toByteArray()
        fos.write(bytes)
        fos.close()
    }

    /**
     * 写图片文件到SD卡
     *
     * @throws IOException
     */
    @Throws(IOException::class)
    fun saveImageToSD(
        ctx: Context?, filePath: String,
        bitmap: Bitmap?, quality: Int
    ) {
        if (bitmap != null) {
            val file = File(
                filePath.substring(
                    0,
                    filePath.lastIndexOf(File.separator)
                )
            )
            if (!file.exists()) {
                file.mkdirs()
            }
            val bos = BufferedOutputStream(
                FileOutputStream(filePath)
            )
            bitmap!!.compress(CompressFormat.JPEG, quality, bos)
            bos.flush()
            bos.close()
            if (ctx != null) {
                scanPhoto(ctx!!, filePath)
            }
        }
    }

    @Throws(IOException::class)
    fun saveBackgroundImage(
        ctx: Context?, filePath: String,
        bitmap: Bitmap?, quality: Int
    ) {
        if (bitmap != null) {
            val file = File(
                filePath.substring(
                    0,
                    filePath.lastIndexOf(File.separator)
                )
            )
            if (!file.exists()) {
                file.mkdirs()
            }
            val bos = BufferedOutputStream(
                FileOutputStream(filePath)
            )
            bitmap!!.compress(CompressFormat.PNG, quality, bos)
            bos.flush()
            bos.close()
            if (ctx != null) {
                scanPhoto(ctx!!, filePath)
            }
        }
    }

    /**
     * 让Gallery上能马上看到该图片
     */
    private fun scanPhoto(ctx: Context, imgFileName: String) {
        val mediaScanIntent = Intent(
            Intent.ACTION_MEDIA_SCANNER_SCAN_FILE
        )
        val file = File(imgFileName)
        val contentUri = Uri.fromFile(file)
        mediaScanIntent.setData(contentUri)
        ctx.sendBroadcast(mediaScanIntent)
    }

    /**
     * 获取bitmap
     *
     * @param context
     * @param fileName
     * @return
     */
    fun getBitmap(context: Context, fileName: String): Bitmap? {
        var fis: FileInputStream? = null
        var bitmap: Bitmap? = null
        try {
            fis = context.openFileInput(fileName)
            bitmap = BitmapFactory.decodeStream(fis)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
        } finally {
            try {
                fis!!.close()
            } catch (e: Exception) {
            }

        }
        return bitmap
    }

    @JvmOverloads
    fun getBitmapByPath(
        filePath: String,
        opts: BitmapFactory.Options? = null
    ): Bitmap? {
        var fis: FileInputStream? = null
        var bitmap: Bitmap? = null
        try {
            val file = File(filePath)
            fis = FileInputStream(file)
            bitmap = BitmapFactory.decodeStream(fis, null, opts)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
        } finally {
            try {
                fis!!.close()
            } catch (e: Exception) {
            }

        }
        return bitmap
    }

    /**
     * 获取bitmap
     *
     * @param file
     * @return
     */
    fun getBitmapByFile(file: File): Bitmap? {
        var fis: FileInputStream? = null
        var bitmap: Bitmap? = null
        try {
            fis = FileInputStream(file)
            bitmap = BitmapFactory.decodeStream(fis)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
        } finally {
            try {
                fis!!.close()
            } catch (e: Exception) {
            }

        }
        return bitmap
    }

    /**
     * 判断当前Url是否标准的content://样式，如果不是，则返回绝对路径
     *
     * @param
     * @return
     */
    fun getAbsolutePathFromNoStandardUri(mUri: Uri): String? {
        var filePath: String? = null

        var mUriString = mUri.toString()
        mUriString = Uri.decode(mUriString)

        val pre1 = "file://" + SDCARD + File.separator
        val pre2 = "file://" + SDCARD_MNT + File.separator

        if (mUriString.startsWith(pre1)) {
            filePath = (Environment.getExternalStorageDirectory().path
                    + File.separator + mUriString.substring(pre1.length))
        } else if (mUriString.startsWith(pre2)) {
            filePath = (Environment.getExternalStorageDirectory().path
                    + File.separator + mUriString.substring(pre2.length))
        }
        return filePath
    }

    /**
     * 通过uri获取文件的绝对路径
     *
     * @param uri
     * @return
     */
    @SuppressWarnings("deprecation")
    fun getAbsoluteImagePath(context: Activity, uri: Uri): String {
        var imagePath = ""
        val proj = arrayOf<String>(MediaStore.Images.Media.DATA)
        val cursor = context.managedQuery(uri, proj, null, null, null)// Which columns to
        // return
        // WHERE clause; which rows to return (all rows)
        // WHERE clause selection arguments (none)
        // Order-by clause (ascending by name)

        if (cursor != null) {
            val column_index = cursor!!
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            if (cursor!!.getCount() > 0 && cursor!!.moveToFirst()) {
                imagePath = cursor!!.getString(column_index)
            }
        }

        return imagePath
    }

    /**
     * 获取图片缩略图 只有Android2.1以上版本支持
     *
     * @param imgName
     * @param kind
     * MediaStore.Images.Thumbnails.MICRO_KIND
     * @return
     */
    @SuppressWarnings("deprecation")
    fun loadImgThumbnail(
        context: Activity, imgName: String,
        kind: Int
    ): Bitmap? {
        var bitmap: Bitmap? = null

        val proj = arrayOf<String>(MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME)

        val cursor = context.managedQuery(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proj,
            MediaStore.Images.Media.DISPLAY_NAME + "='" + imgName + "'", null, null
        )

        if (cursor != null && cursor.count > 0 && cursor.moveToFirst()) {
            val crThumb = context.contentResolver
            val options = BitmapFactory.Options()
            options.inSampleSize = 1
            bitmap = MediaStore.Images.Thumbnails.getThumbnail(
                crThumb, cursor.getInt(0).toLong(),
                kind, options
            )
        }
        return bitmap
    }

    fun loadImgThumbnail(filePath: String, w: Int, h: Int): Bitmap? {
        val bitmap = getBitmapByPath(filePath)
        return zoomBitmap(bitmap, w, h)
    }

    /**
     * 获取SD卡中最新图片路径
     *
     * @return
     */
    fun getLatestImage(context: Activity): String? {
        var latestImage: String? = null
        val items = arrayOf<String>(MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA)
        val cursor = context.managedQuery(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, items, null, null, MediaStore.Images.Media._ID + " desc"
        )

        if (cursor != null && cursor!!.getCount() > 0) {
            cursor!!.moveToFirst()
            cursor!!.moveToFirst()
            while (!cursor!!.isAfterLast()) {
                latestImage = cursor!!.getString(1)
                break
                cursor!!
                    .moveToNext()
            }
        }

        return latestImage
    }

    /**
     * 计算缩放图片的宽高
     *
     * @param img_size
     * @param square_size
     * @return
     */
    fun scaleImageSize(img_size: IntArray, square_size: Int): IntArray {
        if (img_size[0] <= square_size && img_size[1] <= square_size)
            return img_size
        val ratio = square_size / Math.max(img_size[0], img_size[1]) as Double
        return intArrayOf((img_size[0] * ratio).toInt(), (img_size[1] * ratio).toInt())
    }

    /**
     * 创建缩略图
     *
     * @param context
     * @param largeImagePath
     * 原始大图路径
     * @param thumbfilePath
     * 输出缩略图路径
     * @param square_size
     * 输出图片宽度
     * @param quality
     * 输出图片质量
     * @throws IOException
     */
    @Throws(IOException::class)
    fun createImageThumbnail(
        context: Context,
        largeImagePath: String, thumbfilePath: String, square_size: Int,
        quality: Int
    ) {
        val opts = BitmapFactory.Options()
        opts.inSampleSize = 1
        // 原始图片bitmap
        val cur_bitmap = getBitmapByPath(largeImagePath, opts) ?: return

// 原始图片的高宽
        val cur_img_size = intArrayOf(cur_bitmap.getWidth(), cur_bitmap.getHeight())
        // 计算原始图片缩放后的宽高
        val new_img_size = scaleImageSize(cur_img_size, square_size)
        // 生成缩放后的bitmap
        val thb_bitmap = zoomBitmap(
            cur_bitmap, new_img_size[0],
            new_img_size[1]
        )
        // 生成缩放后的图片文件
        saveImageToSD(null, thumbfilePath, thb_bitmap, quality)
    }

    /**
     * 放大缩小图片
     *
     * @param bitmap
     * @param w
     * @param h
     * @return
     */
    fun zoomBitmap(bitmap: Bitmap?, w: Int, h: Int): Bitmap? {
        var newbmp: Bitmap? = null
        if (bitmap != null) {
            val width = bitmap!!.getWidth()
            val height = bitmap!!.getHeight()
            val matrix = Matrix()
            val scaleWidht = w.toFloat() / width
            val scaleHeight = h.toFloat() / height
            matrix.postScale(scaleWidht, scaleHeight)
            newbmp = Bitmap.createBitmap(
                bitmap, 0, 0, width, height, matrix,
                true
            )
        }
        return newbmp
    }

    fun scaleBitmap(bitmap: Bitmap): Bitmap {
        // 获取这个图片的宽和高
        val width = bitmap.getWidth()
        val height = bitmap.getHeight()
        // 定义预转换成的图片的宽度和高度
        val newWidth = 200
        val newHeight = 200
        // 计算缩放率，新尺寸除原始尺寸
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // 创建操作图片用的matrix对象
        val matrix = Matrix()
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight)
        // 旋转图片 动作
        // matrix.postRotate(45);
        // 创建新的图片
        return Bitmap.createBitmap(
            bitmap, 0, 0, width, height,
            matrix, true
        )
    }

    /**
     * (缩放)重绘图片
     *
     * @param context
     * Activity
     * @param bitmap
     * @return
     */
    fun reDrawBitMap(context: Activity, bitmap: Bitmap): Bitmap {
        val dm = DisplayMetrics()
        context.getWindowManager().getDefaultDisplay().getMetrics(dm)
        val rHeight = dm.heightPixels
        val rWidth = dm.widthPixels
        // float rHeight=dm.heightPixels/dm.density+0.5f;
        // float rWidth=dm.widthPixels/dm.density+0.5f;
        // int height=bitmap.getScaledHeight(dm);
        // int width = bitmap.getScaledWidth(dm);
        val height = bitmap.getHeight()
        val width = bitmap.getWidth()
        val zoomScale: Float

        /** 方式3  */
        if (width >= rWidth)
            zoomScale = rWidth.toFloat() / width
        else
            zoomScale = 1.0f
        // 创建操作图片用的matrix对象
        val matrix = Matrix()
        // 缩放图片动作
        matrix.postScale(zoomScale, zoomScale)
        return Bitmap.createBitmap(
            bitmap, 0, 0,
            bitmap.getWidth(), bitmap.getHeight(), matrix, true
        )
    }

    /**
     * 将Drawable转化为Bitmap
     *
     * @param drawable
     * @return
     */
    fun drawableToBitmap(drawable: Drawable): Bitmap {
        val width = drawable.getIntrinsicWidth()
        val height = drawable.getIntrinsicHeight()
        val bitmap = Bitmap.createBitmap(
            width, height, if (drawable
                    .getOpacity() !== PixelFormat.OPAQUE
            )
                Config.ARGB_8888
            else
                Config.RGB_565
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, width, height)
        drawable.draw(canvas)
        return bitmap

    }

    /**
     * 获得圆角图片的方法
     *
     * @param bitmap
     * @param roundPx
     * 一般设成14
     * @return
     */
    fun getRoundedCornerBitmap(bitmap: Bitmap, roundPx: Float): Bitmap {

        val output = Bitmap.createBitmap(
            bitmap.getWidth(),
            bitmap.getHeight(), Config.ARGB_8888
        )
        val canvas = Canvas(output)

        val color = -0xbdbdbe
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.getWidth(), bitmap.getHeight())
        val rectF = RectF(rect)

        paint.setAntiAlias(true)
        canvas.drawARGB(0, 0, 0, 0)
        paint.setColor(color)
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint)

        paint.setXfermode(PorterDuffXfermode(Mode.SRC_IN))
        canvas.drawBitmap(bitmap, rect, rect, paint)

        return output
    }

    /**
     * 获得带倒影的图片方法
     *
     * @param bitmap
     * @return
     */
    fun createReflectionImageWithOrigin(bitmap: Bitmap): Bitmap {
        val reflectionGap = 4
        val width = bitmap.getWidth()
        val height = bitmap.getHeight()

        val matrix = Matrix()
        matrix.preScale(1F, -1F)

        val reflectionImage = Bitmap.createBitmap(
            bitmap, 0, height / 2,
            width, height / 2, matrix, false
        )

        val bitmapWithReflection = Bitmap.createBitmap(
            width,
            height + height / 2, Config.ARGB_8888
        )

        val canvas = Canvas(bitmapWithReflection)
        canvas.drawBitmap(bitmap, 0F, 0F, null)
        val deafalutPaint = Paint()
        canvas.drawRect(0F, height.toFloat(), width.toFloat(), height.toFloat() + reflectionGap, deafalutPaint)

        canvas.drawBitmap(reflectionImage, 0F, (height + reflectionGap).toFloat(), null)

        val paint = Paint()
        val shader = LinearGradient(0F, bitmap.height.toFloat(), 0F, (bitmapWithReflection.height + reflectionGap).toFloat(), 0x70ffffff, 0x00ffffff, TileMode.CLAMP)
        paint.shader = shader
        // Set the Transfer mode to be porter duff and destination in
        paint.xfermode = PorterDuffXfermode(Mode.DST_IN)
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(
            0F,
            height.toFloat(),
            width.toFloat(),
            bitmapWithReflection.height.toFloat() + reflectionGap,
            paint
        )

        return bitmapWithReflection
    }

    /**
     * 将bitmap转化为drawable
     *
     * @param bitmap
     * @return
     */
    fun bitmapToDrawable(bitmap: Bitmap): Drawable {
        return BitmapDrawable(bitmap)
    }

    /**
     * 获取图片类型
     *
     * @param file
     * @return
     */
    fun getImageType(file: File?): String? {
        if (file == null || !file!!.exists()) {
            return null
        }
        var `in`: InputStream? = null
        try {
            `in` = FileInputStream(file)
            return getImageType(`in`)
        } catch (e: IOException) {
            return null
        } finally {
            try {
                if (`in` != null) {
                    `in`!!.close()
                }
            } catch (e: IOException) {
            }

        }
    }

    /**
     * 获取图片的类型信息
     *
     * @param in
     * @return
     * @see .getImageType
     */
    fun getImageType(`in`: InputStream?): String? {
        if (`in` == null) {
            return null
        }
        try {
            val bytes = ByteArray(8)
            `in`!!.read(bytes)
            return getImageType(bytes)
        } catch (e: IOException) {
            return null
        }

    }

    /**
     * 获取图片的类型信息
     *
     * @param bytes
     * 2~8 byte at beginning of the image file
     * @return image mimetype or null if the file is not image
     */
    fun getImageType(bytes: ByteArray): String? {
        if (isJPEG(bytes)) {
            return "image/jpeg"
        }
        if (isGIF(bytes)) {
            return "image/gif"
        }
        if (isPNG(bytes)) {
            return "image/png"
        }
        return if (isBMP(bytes)) {
            "application/x-bmp"
        } else null
    }

    private fun isJPEG(b: ByteArray): Boolean {
        return if (b.size < 2) {
            false
        } else b[0] == 0xFF.toByte() && b[1] == 0xD8.toByte()
    }

    private fun isGIF(b: ByteArray): Boolean {
        return if (b.size < 6) {
            false
        } else b[0] == 'G'.toByte() && b[1] == 'I'.toByte() && b[2] == 'F'.toByte() && b[3] == '8'.toByte()
                && (b[4] == '7'.toByte() || b[4] == '9'.toByte()) && b[5] == 'a'.toByte()
    }

    private fun isPNG(b: ByteArray): Boolean {
        return if (b.size < 8) {
            false
        } else b[0] == 137.toByte() && b[1] == 80.toByte() && b[2] == 78.toByte()
                && b[3] == 71.toByte() && b[4] == 13.toByte() && b[5] == 10.toByte()
                && b[6] == 26.toByte() && b[7] == 10.toByte()
    }

    private fun isBMP(b: ByteArray): Boolean {
        return if (b.size < 2) {
            false
        } else b[0].toInt() == 0x42 && b[1].toInt() == 0x4d
    }

    /**
     * 获取图片路径
     *
     * @param uri
     * @param
     */
    fun getImagePath(uri: Uri, context: Activity): String {

        val projection = arrayOf<String>(MediaColumns.DATA)
        val cursor = context.getContentResolver().query(uri, projection, null, null, null)
        if (cursor != null) {
            cursor!!.moveToFirst()
            val columIndex = cursor!!.getColumnIndexOrThrow(MediaColumns.DATA)
            val ImagePath = cursor!!.getString(columIndex)
            cursor!!.close()
            return ImagePath
        }

        return uri.toString()
    }

    fun loadPicasaImageFromGalley(
        uri: Uri,
        context: Activity
    ): Bitmap? {

        val projection = arrayOf<String>(MediaColumns.DATA, MediaColumns.DISPLAY_NAME)
        val cursor = context.getContentResolver().query(uri, projection, null, null, null)
        if (cursor != null) {
            cursor.moveToFirst()

            val columIndex = cursor.getColumnIndex(MediaColumns.DISPLAY_NAME)
            if (columIndex != -1) {
                Thread(Runnable {
                    try {
                        bitmap = MediaStore.Images.Media
                            .getBitmap(
                                context.contentResolver,
                                uri
                            )
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }).start()
            }
            cursor.close()
            return bitmap
        } else
            return null
    }

    /** */

    /**
     * 压缩Bitmap,同时使用两种策略压缩,先压缩宽高，再压缩质量
     *
     * @return 存储Bitmap的文件
     * @throws IOException
     */
    @Throws(IOException::class)
    fun compressBitmap(url: String, storageDir: String, prefix: String): File? {
        if (!TextUtils.isEmpty(url)) {
            val img = File(url)
            var bitmap = revitionImageSize(img)
            bitmap = compressImage(bitmap!!)
            return convertToFile(bitmap, storageDir, prefix)
        }
        return null
    }


    /**
     * 使用矩阵缩放图片至期待的宽高
     *
     * @param source       被缩放的图片
     * @param expectWidth  期待的宽
     * @param expectHeight 期待的高
     * @return 返回压缩后的图片
     */
    fun zoomBitmap(source: Bitmap, expectWidth: Float, expectHeight: Float): Bitmap {
        // 获取这个图片的宽和高
        val width = source.getWidth()
        val height = source.getHeight()
        // 创建操作图片用的matrix对象
        val matrix = Matrix()
        //默认不缩放
        var scaleWidth = 1f
        var scaleHeight = 1f
        // 计算宽高缩放率
        if (expectWidth < width) {
            scaleWidth = expectWidth / width
        }
        if (expectHeight < height) {
            scaleHeight = expectHeight / height
        }
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(
            source, 0, 0, width.toInt(),
            height.toInt(), matrix, true
        )
    }

    //压缩图片大小
    @Throws(IOException::class)
    fun revitionImageSize(file: File): Bitmap? {
        var `in` = BufferedInputStream(FileInputStream(file))
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeStream(`in`, null, options)
        `in`.close()
        var i = 1
        var bitmap: Bitmap? = null
        while (true) {
            if (options.outWidth / i <= 600 && options.outHeight / i <= 600) {
                `in` = BufferedInputStream(
                    FileInputStream(file)
                )
                options.inSampleSize = i
                options.inJustDecodeBounds = false
                bitmap = BitmapFactory.decodeStream(`in`, null, options)
                break
            }
            i += 1
        }
        return bitmap
    }


    //压缩图片质量
    fun compressImage(image: Bitmap): Bitmap {

        val baos = ByteArrayOutputStream()
        image.compress(CompressFormat.JPEG, 100, baos)//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        var offset = 100
        while (baos.toByteArray().size / 1024 > MAX_SIZE) {  //循环判断如果压缩后图片是否大于200kb,大于继续压缩

            baos.reset()//重置baos即清空baos
            image.compress(CompressFormat.JPEG, offset, baos)//这里压缩options%，把压缩后的数据存放到baos中
            offset -= 10//每次都减少10
        }
        val isBm = ByteArrayInputStream(baos.toByteArray())
        //把压缩后的数据baos存放到ByteArrayInputStream中
        return BitmapFactory.decodeStream(isBm, null, null)
    }

    /**
     * 将bitmap写入一个file中
     *
     * @return 保存bitmap的file对象
     */
    @Throws(IOException::class)
    fun convertToFile(bitmap: Bitmap?, storageDir: String, prefix: String): File {
        var cacheDir = checkTargetCacheDir(storageDir)
        //以时间戳生成一个临时文件名称
        cacheDir = createFile(cacheDir!!, prefix, ".jpg")
        var created = false//是否创建成功,默认没有创建
        if (!cacheDir!!.exists()) created = cacheDir!!.createNewFile()
        if (created)
        //将图片写入目标file,100表示不压缩,Note:png是默认忽略这个参数的
            bitmap!!.compress(CompressFormat.PNG, 100, FileOutputStream(cacheDir))
        return cacheDir
    }

    /**
     * 检查目标缓存目录是否存在，如果存在则返回这个目录，如果不存在则新建这个目录
     *
     * @return
     */
    fun checkTargetCacheDir(storageDir: String): File? {

        var file: File? = null
        file = File(storageDir)

        if (!file!!.exists()) {
            file!!.mkdirs()//创建目录
        }

        return if (file != null && file!!.exists())
            file//文件已经被成功创建
        else {
            null//即时经过以上检查，文件还是没有被准确的创建
        }
    }

    /**
     * 根据系统时间、前缀、后缀产生一个文件
     */
    fun createFile(folder: File, prefix: String, suffix: String): File {
        if (!folder.exists() || !folder.isDirectory()) folder.mkdirs()
        val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.CHINA)
        val filename = prefix + dateFormat.format(Date(System.currentTimeMillis())) + suffix
        return File(folder, filename)
    }

    /**
     * android图片压缩工具
     * 压缩多张图片 RxJava 方式
     */
    fun compressWithRx(files: List<String>, observer: Observer<Any>) {

        Luban.get(getContext())
            .load(files)
            .putGear(Luban.THIRD_GEAR)
            .asListObservable()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { throwable -> throwable.printStackTrace() }
            .onErrorResumeNext(Function<Throwable, ObservableSource<out File>> { Observable.empty() })
            .subscribe(observer)
    }

    /**
     * android图片压缩工具
     * 压缩单张图片 RxJava 方式
     */
    fun compressWithRx(url: String, consumer: Consumer<Any>) {

        Luban.get(getContext())
            .load(url)
            .putGear(Luban.THIRD_GEAR)
            .asObservable()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { throwable -> throwable.printStackTrace() }
            .onErrorResumeNext(Function<Throwable, ObservableSource<out File>> { Observable.empty() })
            .subscribe(consumer)
    }

}

