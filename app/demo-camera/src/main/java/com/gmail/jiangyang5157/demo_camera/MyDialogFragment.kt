package com.gmail.jiangyang5157.demo_camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.DialogFragment
import com.gmail.jiangyang5157.demo_camera.ui.theme.AppTheme
import androidx.compose.ui.window.Dialog
import android.graphics.Rect
import androidx.annotation.IntDef
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.common.InputImage

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.graphics.RectF
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.core.resolutionselector.AspectRatioStrategy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.lifecycle.LifecycleOwner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import androidx.annotation.IntRange
import android.graphics.Matrix
import android.graphics.Outline
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.util.Size
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import android.widget.TextView
import androidx.camera.core.resolutionselector.ResolutionStrategy
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.max
import java.nio.ByteBuffer
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.geometry.Size as ComposeSize

class MyDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setOnShowListener {
                window?.setBackgroundDrawableResource(android.R.color.transparent)
//                window?.setBackgroundDrawableResource(android.R.color.holo_red_light)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
//                    PaymentQrCodeDialogScreen(lifecycleOwner = this@MyDialogFragment)
                    CameraScreen()
                }
            }
        }
    }
}

@Composable
private fun CameraScreen() {
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenWidth = with(density) { configuration.screenWidthDp }
    val screenHeight = with(density) { configuration.screenHeightDp }
    Log.d("####", "screen: $screenWidth $screenHeight")

    var previewSizePx by remember { mutableStateOf<Size?>(null) } // Preview size in Pixels
    var previewRotations by remember { mutableStateOf(0) }
    var layoutWidthDp by remember { mutableStateOf(0.dp) }

    val previewSizeDp: androidx.compose.ui.geometry.Size? = previewSizePx?.let { sizePx ->
        with(density) {
            androidx.compose.ui.geometry.Size(sizePx.width.toDp().value, sizePx.height.toDp().value)
        }
    }

    val viewModifier = previewSizeDp?.let { sizeDp ->
        if (sizeDp.width == 0f || sizeDp.height == 0f || layoutWidthDp == 0.dp) {
            Modifier
        } else {
            val targetHeightDp: Dp
            val targetWidthDp: Dp

            if (previewRotations == 90 || previewRotations == 270) {
                targetHeightDp = layoutWidthDp * sizeDp.width / sizeDp.height
                targetWidthDp = layoutWidthDp
            } else {
                targetHeightDp = layoutWidthDp * sizeDp.height / sizeDp.width
                targetWidthDp = layoutWidthDp
            }

            Log.d(
                "####",
                "targetHeightDp=$targetHeightDp, targetWidthDp=$targetWidthDp, previewRotations=$previewRotations, previewSizeDp=$previewSizeDp"
            )

            Modifier.size(targetWidthDp, targetHeightDp)
        }
    } ?: Modifier

    Surface(
        modifier = Modifier,
        color = Color.Red,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged { intSize ->
                    layoutWidthDp = with(density) { intSize.width.toDp() }
                    Log.d("####", "layoutWidthDp=$layoutWidthDp")
                },
        ) {
            Text("TOP")
            Box(
                modifier = viewModifier
            ) {
                SimpleCameraPreview(
                    onResolutionChanged = { resolution, rotationDegrees ->
                        previewSizePx = resolution
                        previewRotations = rotationDegrees
                        Log.d("####", "Preview size changed: $resolution $rotationDegrees")
                    }
                )
            }
            Text("BOTTOM")
        }
    }
}

@Composable
fun SimpleCameraPreview(
    modifier: Modifier = Modifier,
    onResolutionChanged: (Size, Int) -> Unit = { _, _ -> },
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val context = LocalContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val executor = remember { Executors.newSingleThreadExecutor() }
    val previewView = remember {
        PreviewView(context).apply {
            scaleType = PreviewView.ScaleType.FILL_START
//            setBackgroundResource(android.R.color.transparent)
        }
    }

    var hasCameraPermission by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCameraPermission = granted
        },
    )
    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA,
            ) == PermissionChecker.PERMISSION_GRANTED
        ) {
            hasCameraPermission = true
        } else {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }

    LaunchedEffect(Unit) {
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.surfaceProvider = previewView.surfaceProvider
            }
            val cameraSelector =
                if (cameraProvider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA)) {
                    CameraSelector.DEFAULT_BACK_CAMERA
                } else CameraSelector.DEFAULT_FRONT_CAMERA
            // Image Analysis
            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalysis.setAnalyzer(executor, SimpleImageAnalyzer())
            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageAnalysis,
                )

                preview.resolutionInfo?.let { info ->
                    onResolutionChanged(info.resolution, info.rotationDegrees)
                }

            } catch (exc: Exception) {
                Log.e("####", "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(context))
    }
    AndroidView(
        modifier = modifier,
        factory = { previewView },
    )
}

class SimpleImageAnalyzer : ImageAnalysis.Analyzer {
    override fun analyze(image: ImageProxy) {
        try {
//            Log.d("####", "${image.width}x${image.height} - ${image.imageInfo.rotationDegrees}")
        } catch (e: Exception) {
            Log.e("####", "${e.message}")
        } finally {
            image.close()
        }
    }
}


//@Composable
//fun PaymentQrCodeDialogScreen(
//    lifecycleOwner: LifecycleOwner? = null,
//    onQrCodeDecrypted: (String) -> Unit = {},
//    onError: () -> Unit = {},
//) {
//    Surface(
//        modifier = Modifier
//            .fillMaxWidth()
//            .heightIn(max = LocalConfiguration.current.screenHeightDp.dp * 0.5f),
//        color = Color.LightGray,
//        shape = RoundedCornerShape(16.dp),
//    ) {
//        PaymentQrCodeDialogContent(
//            modifier = Modifier
//                .background(Color.Red)
//                .padding(vertical = 12.dp),
//            lifecycleOwner = lifecycleOwner,
//            onQrCodeDecrypted = onQrCodeDecrypted,
//            onError = onError,
//        )
//    }
//}
//
//@Composable
//fun PaymentQrCodeDialogContent(
//    modifier: Modifier = Modifier,
//    lifecycleOwner: LifecycleOwner? = null,
//    onQrCodeDecrypted: (String) -> Unit = {},
//    onError: () -> Unit = {},
//) {
//    var layoutWidth by remember { mutableStateOf<Int>(0) }
//    var previewSize by remember { mutableStateOf<Size?>(null) }
//    var previewRotations by remember { mutableStateOf<Int>(0) }
//
//    Column(
//        modifier = modifier.onSizeChanged { intSize ->
//            layoutWidth = intSize.width
//            Log.d("####", "layoutWidth=$intSize")
//        }
//    ) {
////        val viewModifier = previewSize.let { previewSize ->
////            if (previewSize == null || previewSize.width == 0 || previewSize.height == 0) {
////                Modifier
////            } else {
////                if (previewRotations == 90) {
////                    val maxHeight = if (layoutWidth > 0) {
////                        (layoutWidth.toFloat() * previewSize.height / previewSize.width).toInt()
////                    } else {
////                        0
////                    }
////
////                    Log.d("####", "maxHeight=$maxHeight")
////                    Modifier
////                        .width(layoutWidth.dp / 10)
////                        .height(maxHeight.dp / 10)
////                } else {
////                    val maxHeight = if (layoutWidth > 0) {
////                        (layoutWidth.toFloat() * previewSize.height / previewSize.width).toInt()
////                    } else {
////                        0
////                    }
////                    Log.d("####", "maxHeight=$maxHeight")
////
////                    Modifier
////                        .width(layoutWidth.dp / 10)
////                        .height(maxHeight.dp / 10)
////                }
////            }
////        }
//
//        lifecycleOwner?.let {
//            PaymentQrCodeBarcodeScannerView(
//                modifier = Modifier
//                    .height(200.dp),
////                    .weight(1f),
//                lifecycleOwner = it,
//                onResolutionChanged = { resolution, rotationDegrees ->
//                    previewSize = resolution
//                    previewRotations = rotationDegrees
//                    Log.d("####", "previewSize=$resolution previewRotations=$rotationDegrees")
//                },
//                onQrCodeDecrypted = onQrCodeDecrypted,
//                onError = onError,
//            )
//            Text(
//                modifier = Modifier.background(Color.Blue),
//                text = "asd",
//            )
//        }
//    }
//
////    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
////        val (preview, description) = createRefs()
////
////        lifecycleOwner?.let {
////            PaymentQrCodeBarcodeScannerView(
////                modifier = Modifier
////                    .constrainAs(preview) {
////                        top.linkTo(parent.top)
////                        start.linkTo(parent.start)
////                        end.linkTo(parent.end)
////                        width = Dimension.matchParent
////                        height = Dimension.ratio("3:4")  // Example: maintain 3:4 aspect ratio
////                    },
////                lifecycleOwner = it,
////                onQrCodeDecrypted = onQrCodeDecrypted,
////                onError = onError
////            )
////
////            Text(
////                modifier = Modifier
////                    .constrainAs(description) {
////                        top.linkTo(preview.bottom)
////                        start.linkTo(parent.start)
////                        end.linkTo(parent.end)
////                        width = Dimension.matchParent
////                    },
////                text = "Some description or instructions",
////                color = Color.Black,
////            )
////        }
////    }
//}
//
//@Composable
//fun PaymentQrCodeBarcodeScannerView(
//    modifier: Modifier = Modifier,
//    lifecycleOwner: LifecycleOwner,
//    onResolutionChanged: (Size, Int) -> Unit = { _, _ -> },
//    onQrCodeDecrypted: (String) -> Unit = {},
//    onError: () -> Unit = {},
//) {
//    BarcodeScannerView(
//        modifier = modifier,
//        lifecycleOwner = lifecycleOwner,
//        acceptTypes = listOf(
//            BarcodeValueType.TEXT,
//            BarcodeValueType.URL,
//        ),
//        onResolutionChanged = onResolutionChanged,
//        onResult = { value ->
//            if (!value.isNullOrBlank()) {
//                onQrCodeDecrypted(value)
//                true
//            } else {
//                false
//            }
//        },
//        onError = { _ ->
//            onError()
//        },
//    )
//}
//
//enum class BarcodeValueType(val type: Int) {
//    TEXT(Barcode.TYPE_TEXT),
//    URL(Barcode.TYPE_URL),
//}
//
//@Composable
//fun BarcodeScannerView(
//    modifier: Modifier = Modifier,
//    lifecycleOwner: LifecycleOwner,
//    acceptTypes: List<BarcodeValueType> = listOf(BarcodeValueType.TEXT),
//    onResolutionChanged: (Size, Int) -> Unit = { _, _ -> },
//    onResult: ((String?) -> Boolean),
//    onError: ((Exception) -> Unit),
//) {
//    val context = LocalContext.current
//    val coroutineScope = rememberCoroutineScope()
//    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
//    val cameraExecutor: ExecutorService = remember { Executors.newSingleThreadExecutor() }
//    var previewBoundingBox by remember { mutableStateOf<RectF?>(null) }
//
//    var hasCameraPermission by remember { mutableStateOf(false) }
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestPermission(),
//        onResult = { granted ->
//            hasCameraPermission = granted
//        },
//    )
//    LaunchedEffect(Unit) {
//        if (ContextCompat.checkSelfPermission(
//                context,
//                Manifest.permission.CAMERA,
//            ) == PermissionChecker.PERMISSION_GRANTED
//        ) {
//            hasCameraPermission = true
//        } else {
//            launcher.launch(Manifest.permission.CAMERA)
//        }
//    }
//
//    if (hasCameraPermission) {
//        val supportedSizes = remember { mutableStateListOf<Size>() }
//        LaunchedEffect(Unit) {
//            val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
//            try {
//                val cameraId = cameraManager.cameraIdList.firstOrNull() ?: return@LaunchedEffect
//                val characteristics = cameraManager.getCameraCharacteristics(cameraId)
//                val streamConfigMap =
//                    characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
//                        ?: return@LaunchedEffect
//                val outputSizes =
//                    streamConfigMap.getOutputSizes(PreviewView::class.java) ?: emptyArray()
//                supportedSizes.addAll(outputSizes)
//                Log.d("####", "supportedSizes.size=${supportedSizes.size}")
//                supportedSizes.forEach { Log.d("####", "supportedSize=$it") }
//            } catch (e: Exception) {
//                Log.e("####", "Error getting camera resolutions", e)
//            }
//        }
////        val resolutionSelector = if (supportedSizes.isNotEmpty()) {
////            val bestSize = supportedSizes.minByOrNull {
////                val sizeRatio = it.width.toFloat() / it.height.toFloat()
////                abs(sizeRatio - 4f / 3f)
////            }
////            Log.d("####", "bestSize=${bestSize}")
////            bestSize?.let { size ->
////                ResolutionSelector.Builder()
////                    .setResolutionStrategy(
////                        ResolutionStrategy(
////                            size,
////                            ResolutionStrategy.FALLBACK_RULE_CLOSEST_HIGHER_THEN_LOWER,
////                        )
////                    )
////                    .build()
////            } ?: ResolutionSelector.Builder()
////                .setAspectRatioStrategy(AspectRatioStrategy.RATIO_4_3_FALLBACK_AUTO_STRATEGY)
////                .build()
////        } else {
////            ResolutionSelector.Builder()
////                .setAspectRatioStrategy(AspectRatioStrategy.RATIO_4_3_FALLBACK_AUTO_STRATEGY)
////                .build()
////        }
//        val resolutionSelector = ResolutionSelector.Builder()
//            .setAspectRatioStrategy(AspectRatioStrategy.RATIO_4_3_FALLBACK_AUTO_STRATEGY)
//            .build()
//
//        val previewView = remember {
//            PreviewView(context).apply {
//                scaleType = PreviewView.ScaleType.FILL_START
////                layoutParams = FrameLayout.LayoutParams(
////                    50,
////                    50,
////                )
//            }
//        }
//
//        Box(
//            modifier = modifier
//        ) {
//            AndroidView(
//                modifier = Modifier.fillMaxWidth(),
//                factory = { _ ->
//                    val preview = Preview.Builder()
//                        .setResolutionSelector(resolutionSelector)
//                        .build()
//                        .also {
//                            // Set surface provider first
//                            it.surfaceProvider = previewView.surfaceProvider
//                        }
//
//                    // Set after preview created
//                    previewView.implementationMode = PreviewView.ImplementationMode.PERFORMANCE
////                    previewView.outlineProvider = previewViewOutlineProvider
////                    previewView.clipToOutline = true
////                    previewView.setBackgroundResource(android.R.color.holo_blue_bright)
//
//                    cameraProviderFuture.addListener(
//                        {
//                            coroutineScope.launch {
//                                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
//                                val options = BarcodeScannerOptions.Builder().setBarcodeFormats(
//                                    Barcode.FORMAT_QR_CODE,
//                                    Barcode.FORMAT_AZTEC,
//                                ).build()
//                                val cameraProvider = cameraProviderFuture.get()
//                                val barcodeScanner = BarcodeScanning.getClient(options)
//                                val imageAnalysis = ImageAnalysis.Builder()
//                                    .setResolutionSelector(resolutionSelector)
//                                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//                                    .build()
//                                    .also { imageAnalysis ->
//                                        val previewImageHelper = PreviewImageHelper()
//                                        imageAnalysis.setAnalyzer(
//                                            cameraExecutor,
//                                            BarcodeAnalyzer(
//                                                scanner = barcodeScanner,
//                                                onBarcodeDetected = { barcode ->
//                                                    if (acceptTypes.any { it.type == barcode.valueType }) {
//                                                        previewBoundingBox =
//                                                            previewImageHelper.mapImageBoxToPreviewBox(
//                                                                previewView.width,
//                                                                previewView.height,
//                                                                previewView.scaleType,
//                                                                barcode.imageWidth,
//                                                                barcode.imageHeight,
//                                                                barcode.imageRotation,
//                                                                barcode.imageBoundingBox,
//                                                            )
//
//                                                        val handled = onResult(barcode.rawValue)
//                                                        if (handled) {
//                                                            cameraProvider.unbindAll()
//                                                        }
//                                                    } else {
//                                                        previewBoundingBox = null
//                                                    }
//                                                },
//                                                onError = onError,
//                                            )
//                                        )
//                                    }
//
//                                try {
//                                    cameraProvider.unbindAll()
//                                    cameraProvider.bindToLifecycle(
//                                        lifecycleOwner,
//                                        cameraSelector,
//                                        preview,
//                                        imageAnalysis
//                                    )
//                                } catch (exc: Exception) {
//                                    Log.e("####", "Camera provider binding failed", exc)
//                                    onError(exc)
//                                }
//
//                                preview.resolutionInfo?.let { info ->
//                                    onResolutionChanged(info.resolution, info.rotationDegrees)
//                                }
//                            }
//                        },
//                        ContextCompat.getMainExecutor(context),
//                    )
//                    previewView
//
////                    TextView(context).apply { text = "hello textview" }
//                },
//            )
//        }
//    }
//}
//
//val previewViewOutlineProvider = object : ViewOutlineProvider() {
//    override fun getOutline(view: View, outline: Outline) {
//        outline.setRoundRect(0, 0, view.width, view.height, 200f)
//    }
//}


//@Retention(AnnotationRetention.SOURCE)
//@IntDef(IMAGE_ROTATION_0, IMAGE_ROTATION_90, IMAGE_ROTATION_180, IMAGE_ROTATION_270)
//internal annotation class ImageRotation
//
//const val IMAGE_ROTATION_0 = 0
//const val IMAGE_ROTATION_90 = 90
//const val IMAGE_ROTATION_180 = 180
//const val IMAGE_ROTATION_270 = 270
//
//internal data class BarcodeWrapper(
//    val rawValue: String?,
//    val valueType: Int,
//    val imageBoundingBox: Rect?,
//    val imageWidth: Int,
//    val imageHeight: Int,
//    // Should be a value in {0, 90, 180, 270}. See [androidx.camera.core.ImageInfo.getRotationDegrees()]
//    @ImageRotation val imageRotation: Int,
//)
//
//internal class BarcodeAnalyzer(
//    private val scanner: BarcodeScanner,
//    private val onBarcodeDetected: (barcode: BarcodeWrapper) -> Unit,
//    private val onError: (Exception) -> Unit,
//) : ImageAnalysis.Analyzer {
//
//    @ExperimentalGetImage
//    override fun analyze(imageProxy: ImageProxy) {
//        imageProxy.image?.let { mediaImage ->
//            val image =
//                InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
//            scanner.process(image).addOnSuccessListener { barcodes ->
//                // process only one barcode per frame
//                barcodes.firstOrNull()?.let { barcode ->
//                    onBarcodeDetected(
//                        BarcodeWrapper(
//                            rawValue = barcode.rawValue,
//                            valueType = barcode.valueType,
//                            imageBoundingBox = barcode.boundingBox,
//                            imageWidth = image.width,
//                            imageHeight = image.height,
//                            imageRotation = image.rotationDegrees,
//                        ),
//                    )
//                }
//            }.addOnFailureListener {
//                onError(it)
//            }.addOnCompleteListener {
//                imageProxy.close()
//            }
//        }
//    }
//}
//
//class PreviewImageHelper {
//
//    /** Returns { rotatedWidth, rotatedHeight } */
//    fun getRotatedImageSize(
//        @IntRange(from = 1) width: Int,
//        @IntRange(from = 1) height: Int,
//        @ImageRotation rotation: Int,
//    ): Pair<Int, Int> {
//        return if (rotation == IMAGE_ROTATION_0 || rotation == IMAGE_ROTATION_180) {
//            width to height
//        } else {
//            height to width
//        }
//    }
//
//    /** Returns scale for the Image in all dimensions */
//    fun getImageScaleFactors(
//        @IntRange(from = 1) previewWidth: Int,
//        @IntRange(from = 1) previewHeight: Int,
//        previewScaleType: PreviewView.ScaleType,
//        @IntRange(from = 1) imageWidth: Int,
//        @IntRange(from = 1) imageHeight: Int,
//    ): Float {
//        return when (previewScaleType) {
//            PreviewView.ScaleType.FILL_CENTER, PreviewView.ScaleType.FILL_START, PreviewView.ScaleType.FILL_END -> {
//                maxOf(previewWidth.toFloat() / imageWidth, previewHeight.toFloat() / imageHeight)
//            }
//
//            PreviewView.ScaleType.FIT_CENTER, PreviewView.ScaleType.FIT_START, PreviewView.ScaleType.FIT_END -> {
//                minOf(previewWidth.toFloat() / imageWidth, previewHeight.toFloat() / imageHeight)
//            }
//        }
//    }
//
//    /** Returns { translateX, translateY } */
//    fun getImageTranslation(
//        previewWidth: Int,
//        previewHeight: Int,
//        previewScaleType: PreviewView.ScaleType,
//        imageWidth: Int,
//        imageHeight: Int,
//        imageScale: Float,
//    ): Pair<Float, Float> {
//        val scaledImageWidth = imageWidth * imageScale
//        val scaledImageHeight = imageHeight * imageScale
//        return when (previewScaleType) {
//            PreviewView.ScaleType.FILL_CENTER, PreviewView.ScaleType.FIT_CENTER -> {
//                (previewWidth - scaledImageWidth) / 2f to (previewHeight - scaledImageHeight) / 2f
//            }
//
//            PreviewView.ScaleType.FILL_START, PreviewView.ScaleType.FIT_START -> {
//                0f to 0f
//            }
//
//            PreviewView.ScaleType.FILL_END, PreviewView.ScaleType.FIT_END -> {
//                previewWidth - scaledImageWidth to previewHeight - scaledImageHeight
//            }
//        }
//    }
//
//    /**
//     * Map imageBox in Image coordinate system to PreviewBox in Preview coordinate system
//     */
//    fun mapImageBoxToPreviewBox(
//        @IntRange(from = 1) previewWidth: Int,
//        @IntRange(from = 1) previewHeight: Int,
//        previewScaleType: PreviewView.ScaleType,
//        @IntRange(from = 1) imageWidth: Int,
//        @IntRange(from = 1) imageHeight: Int,
//        @ImageRotation imageRotation: Int,
//        imageBox: Rect?,
//    ): RectF? {
//        if (
//            imageBox == null ||
//            previewWidth <= 0 ||
//            previewHeight <= 0 ||
//            imageWidth <= 0 ||
//            imageHeight <= 0
//        ) return null
//
//        val (rotatedImageWidth, rotatedImageHeight) = getRotatedImageSize(
//            imageWidth,
//            imageHeight,
//            imageRotation,
//        )
//        val scale = getImageScaleFactors(
//            previewWidth,
//            previewHeight,
//            previewScaleType,
//            rotatedImageWidth,
//            rotatedImageHeight,
//        )
//        val (translateX, translateY) = getImageTranslation(
//            previewWidth,
//            previewHeight,
//            previewScaleType,
//            rotatedImageWidth,
//            rotatedImageHeight,
//            scale,
//        )
//
//        val matrix = Matrix().apply {
//            postScale(scale, scale)
//            postTranslate(translateX, translateY)
//        }
//
//        return RectF(imageBox).apply {
//            matrix.mapRect(this)
//        }
//    }
//}