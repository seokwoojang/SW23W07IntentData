package kr.ac.kumoh.ce.s20190995.sw23w07intentdata

import android.app.Activity
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import kr.ac.kumoh.ce.s20190995.sw23w07intentdata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener {
    companion object{
        const val KEY_NAME = "location"
        const val CAT = "cat"
        const val TREE = "tree"
    }
    private lateinit var main: ActivityMainBinding
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        //아닌 것부터 먼저 쳐내는 코딩을 하면 나중에 팀장님이 좋아하신다
        if(it.resultCode != Activity.RESULT_OK)
            return@registerForActivityResult

        val result = it.data?.getIntExtra(
            ImageActivity.IMAGE_RESULT,
            ImageActivity.NONE) // 아무것도 없으면 NONE을 반환해라
        val str = when (result){
            ImageActivity.LIKE -> "좋아요"
            ImageActivity.DISLIKE -> "싫어요"
            else -> ""
        }
        when (it.data?.getStringExtra(ImageActivity.IMAGE_NAME)){
            CAT -> main.btn1.text = "고양이 ($str)"
            TREE -> main.btn2.text = "나무 ($str)"
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main = ActivityMainBinding.inflate(layoutInflater)
        setContentView(main.root)

        main.btn1.setOnClickListener (this)
        main.btn2.setOnClickListener (this)
    }

    override fun onClick(p0: View?) {
        val intent = Intent(this, AnotherActivity::class.java)
        //ImageActivity::class.java)
        val value = when (p0?.id){
            main.btn1.id -> {
                Toast.makeText(this,"고양이 눌림",Toast.LENGTH_SHORT).show()
                CAT
            }
            main.btn2.id -> {
                Toast.makeText(this,"나무 눌림",Toast.LENGTH_SHORT).show()
                TREE
            }

            else -> return
        }
        intent.putExtra(KEY_NAME,value)
        //startActivity(intent)
        startForResult.launch(intent)
    }
}