package com.ikaru.algostudiotest.Activities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.ikaru.algostudiotest.Models.Meme
import com.ikaru.algostudiotest.R
import com.squareup.picasso.Picasso
import com.vipul.hp_hp.library.Layout_to_Image
import kotlinx.android.synthetic.main.activity_detail.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*


class DetailActivity : AppCompatActivity() {

    lateinit var meme : Meme
    //image pick code
    private val IMAGE_PICK_CODE = 1000;
    //Permission code
    private val PERMISSION_CODE = 1001;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        meme = intent.extras?.get("Meme") as Meme
        Picasso
            .get()
            .load(meme.url) // load the image
            .into(iv_meme_detail) // select the ImageView to load it into

        btn_Add_Text.setOnClickListener {
            showCreateCategoryDialog()
        }

        btn_Add_Icon.setOnClickListener{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    //permission already granted
                    pickImageFromGallery();
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }

        btn_Save.setOnClickListener{
            var layout : ConstraintLayout = memeLayout
            var bitmap : Bitmap
            var layouttoimage = Layout_to_Image(this,layout)
            bitmap = layouttoimage.convert_layout()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    //permission already granted
                    MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, UUID.randomUUID().toString() , "make meme from algotest");
                    Toast.makeText(this@DetailActivity,"Saved to Gallery",Toast.LENGTH_SHORT).show()
                }
            }
            else{
                //system OS is < Marshmallow
                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, UUID.randomUUID().toString() , "make meme from algotest");
                Toast.makeText(this@DetailActivity,"Saved to Gallery",Toast.LENGTH_SHORT).show()
            }


//            saveImageToExternalStorage(bitmap)
        }
    }



    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            iv_meme_icon.setImageURI(data?.data)
        }
    }



    fun showCreateCategoryDialog() {
            val context = this
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Enter Text")

            val view = layoutInflater.inflate(R.layout.layout_text, null)

            val categoryEditText = view.findViewById(R.id.et_meme_text) as EditText

            builder.setView(view);

            builder.setPositiveButton(android.R.string.ok) { dialog, p1 ->
                val newCategory = categoryEditText.text
                var isValid = true
                if (newCategory.isBlank()) {
                    categoryEditText.error = "Cannot Be Empty"
                    isValid = false
                }

                if (isValid) {
                    tv_meme.text = newCategory
                }

                if (isValid) {
                    dialog.dismiss()
                }
            }

            builder.setNegativeButton(android.R.string.cancel) { dialog, p1 ->
                dialog.cancel()
            }

            builder.show();
        }
    }

