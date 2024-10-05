package com.example.androidproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidproject.databinding.ActivityDetailBinding
import com.example.androidproject.ui.theme.AndroidProjectTheme

class DetailActivity2 : ComponentActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var confiResultData : ConfigDataCal? = null
    companion object{
        private const val EXTRA_CONFIG="config"
        fun newIntent(
            context: Context,
            config: ConfigDataCal
        ): Intent {
            return  Intent(context,DetailActivity::class.java).apply {
                putExtra(EXTRA_CONFIG,config)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBackBtn()
        setValue()
    }

    private fun setValue(){
        //vegetables,meat,water,noodles,others
        confiResultData = intent.getParcelableExtra(EXTRA_CONFIG)
        binding.vegetableCal.text = getString(R.string.cal_value,
            confiResultData?.item?.get(0)?.cal ?: 0
        )
        binding.meatCal.text = getString(R.string.cal_value,
            confiResultData?.item?.get(1)?.cal ?: 0
        )
        binding.waterCal.text = getString(R.string.cal_value,
            confiResultData?.item?.get(2)?.cal ?: 0
        )
        binding.noodlesCal.text = getString(R.string.cal_value,
            confiResultData?.item?.get(3)?.cal ?: 0
        )
        binding.otherCal.text = getString(R.string.cal_value,
            confiResultData?.item?.get(4)?.cal ?: 0
        )
    }

    private fun setupBackBtn(){
        binding.backButton.setOnClickListener{
            finish()
        }
    }
}