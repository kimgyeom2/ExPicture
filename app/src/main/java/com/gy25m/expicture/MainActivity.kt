package com.gy25m.expicture


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.gy25m.expicture.databinding.ActivityMainBinding
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainActivity : AppCompatActivity() {
   val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var imageCaptureLauncher: ActivityResultLauncher<Intent>
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btn.setOnClickListener {
            takePicture()
        }

        imageCaptureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                imageUri?.let {
                    Glide.with(this).load(it).into(binding.iv)
                }
            }
        }
    }

    private fun takePicture() {
        val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        pictureIntent.resolveActivity(packageManager)?.also {
            val imageFile: File = createImageFile()
            imageUri = FileProvider.getUriForFile(
                this,
                "com.gy25m.expicture",  // AndroidManifest.xml에서 지정한 FileProvider의 authorities와 동일해야 함.
                imageFile
            )

            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            imageCaptureLauncher.launch(pictureIntent)
        }
    }

    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }
}