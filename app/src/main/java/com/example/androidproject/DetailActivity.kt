package com.example.androidproject

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import com.example.androidproject.database.CSVModifier
import com.example.androidproject.database.SaveMenu
import com.example.androidproject.databinding.ActivityDetailBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailActivity : ComponentActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var confiResultData : ConfigDataCal? = null
    val fileName = R.string.fileName.toString()
    var sum = 0
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
        setupSaveHistory()
        setValue()
        binding.saveButton.visibility = if (confiResultData?.saved == true) View.GONE else View.VISIBLE
    }

    private fun setupSaveHistory() {
        binding.saveButton.setOnClickListener {
            //vegetables,meat,water,noodles,others
            val saveMenuData = SaveMenu(
                confiResultData?.menu?.id!!,
                confiResultData?.name?.value!!,
                confiResultData?.item?.get(1)?.id!!,
                confiResultData?.item?.get(2)?.id!!,
                confiResultData?.item?.get(3)?.id!!,
                confiResultData?.item?.get(0)?.id!!,
                confiResultData?.item?.get(4)?.id!!,
                confiResultData?.category!!,
                confiResultData?.method!!,
                sum,
                ""
            )
            CSVModifier(fileName,this).appendMenu(saveMenuData)
            binding.saveButton.visibility = View.GONE
            val testCSV : MutableList<SaveMenu> = CSVModifier(fileName,this).readMenusFromCSV(true)
            testCSV.forEach{
                Log.d("testCSV",it.toString())
            }
        }
    }

    private fun getCurrentTimestamp(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
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

        val time = if(confiResultData?.time == "") getCurrentTimestamp() else confiResultData?.time
        binding.time.text = time
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
            if(binding.saveButton.visibility == View.GONE){
                setResult(Activity.RESULT_OK)
            }
            else{
                setResult(Activity.RESULT_CANCELED)
            }
            finish()
        }
    }
}