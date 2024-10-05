package com.example.androidproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.example.androidproject.databinding.ActivityDetailBinding

class DetailActivity : ComponentActivity() {
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
        confiResultData?.item?.let { Log.d("checkInputintend", it.joinToString(separator = "") ) }
        binding.menuCal.text = getString(R.string.cal_value, confiResultData?.menu?.cal ?:0 )
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

        var sum = 0
        confiResultData?.item?.forEach {
            sum+=it.cal
        }
        sum+=confiResultData?.menu?.cal?:0
        binding.sumCal.text = getString(R.string.cal_value,sum)

        binding.foodName.text = confiResultData?.name?.value
        binding.otherName.text = confiResultData?.item?.get(4)?.name
    }

    private fun setupBackBtn(){
        binding.backButton.setOnClickListener{
            finish()
        }
    }
}