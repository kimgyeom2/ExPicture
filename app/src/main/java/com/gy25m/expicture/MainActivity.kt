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



class MainActivity : AppCompatActivity() {
   val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }


    // 1. ActivityResultLauncher 인스턴스 선언
    private lateinit var imageCaptureLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btn.setOnClickListener {
            takePicture()
        }
        // 2. registerForActivityResult 사용하여 초기화
        imageCaptureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageBitmap = result.data?.extras?.get("data") as Bitmap
                Glide.with(this).load(imageBitmap).into(binding.iv)
            }
        }
    }

    private fun takePicture() {
        val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        pictureIntent.resolveActivity(packageManager)?.also {
            // 3. launch 메서드를 사용하여 액티비티 시작
            imageCaptureLauncher.launch(pictureIntent)
        }
    }
}