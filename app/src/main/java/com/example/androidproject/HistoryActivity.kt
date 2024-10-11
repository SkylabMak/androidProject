package com.example.androidproject

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.androidproject.database.CSVModifier
import com.example.androidproject.database.CategoryCSVStorage
import com.example.androidproject.database.CookingMethodCSVStorage
import com.example.androidproject.database.Item
import com.example.androidproject.database.MeatCSVStorage
import com.example.androidproject.database.MenuStorage
import com.example.androidproject.database.NoodlesCSVHandler
import com.example.androidproject.database.OtherIngredientsCSVStorage
import com.example.androidproject.database.SaveMenu
import com.example.androidproject.database.StringWrapper
import com.example.androidproject.database.VegetableCSVStorage
import com.example.androidproject.database.WaterCSVStorage
import com.example.androidproject.databinding.ActivityHistoryBinding

class HistoryActivity : ComponentActivity(){
    private lateinit var binding: ActivityHistoryBinding
    //on click


    //on create
    private lateinit var other: OtherIngredientsCSVStorage
    private lateinit var noodles: NoodlesCSVHandler
    private lateinit var water: WaterCSVStorage
    private lateinit var menuStore: MenuStorage
    private lateinit var meats: MeatCSVStorage
    private lateinit var vegetable: VegetableCSVStorage
    private lateinit var cookingMethod: CookingMethodCSVStorage
    private lateinit var category: CategoryCSVStorage

    companion object{
        fun newIntent(
            context: Context,
        ): Intent {
            return  Intent(context,HistoryActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupResource()
        setupBackbtn()
        setupClearBtn()
        setupMenuCard(getHistory ())
    }

    private fun setupResource(){
        other = OtherIngredientsCSVStorage(this)
        noodles = NoodlesCSVHandler(this)
        water = WaterCSVStorage(this)
        menuStore = MenuStorage(this)
        meats = MeatCSVStorage(this)
        vegetable = VegetableCSVStorage(this)
        cookingMethod = CookingMethodCSVStorage(this)
        category = CategoryCSVStorage(this)
    }

    private fun setupClearBtn(){
        binding.historyClearButton.setOnClickListener {
            CSVModifier(R.string.fileName.toString(),this).clearAndCopyCsvToInternalStorage(R.raw.history)
            val container = binding.menuCardContainer
            container.removeAllViews()
        }
    }

    private fun getHistory (): MutableList<SaveMenu>{
        return CSVModifier(R.string.fileName.toString(),this).readMenusFromCSV(true)
    }
    private fun checkNullItem(item: Item?): Item {
        return item ?: Item("", "", 0, 0)
    }
    private fun setupMenuCard(menuList : MutableList<SaveMenu>){
        val container = binding.menuCardContainer
        container.removeAllViews()
        Log.d("loop to add",menuList.size.toString())
        for (menu in menuList) {
            Log.d("loop to add",menu.toString())
            // Inflate the menu_card.xml layout for each menu item
            val menuCardView = layoutInflater.inflate(R.layout.menu_card, container, false)

            // Find and populate the views with data from the SaveMenu object
            val menuNameTextView = menuCardView.findViewById<TextView>(R.id.menuName)
            val calCardTextView = menuCardView.findViewById<TextView>(R.id.calCard)
            val timeTextView = menuCardView.findViewById<TextView>(R.id.time)
            val dateTextView = menuCardView.findViewById<TextView>(R.id.date)
            val detail = menuCardView.findViewById<LinearLayout>(R.id.menuCard)

            // Set the values to the views from the SaveMenu object
            menuNameTextView.text = menu.name
            calCardTextView.text = getString(R.string.cal_value,menu.cal)  // Assuming cal is an integer representing calories
            val datWithTime = menu.timestamp.split(" ")
            dateTextView.text = datWithTime[0]
            timeTextView.text = datWithTime[1]
            detail.setOnClickListener {
                Log.d("testMenu",menu.toString())
                val config = ConfigDataCal(//run data class
                    name = StringWrapper(menu.name),
                    menu = menuStore.getById(menu.id)!!,
                    //vegetables,meat,water,noodles,others
                    item = listOf(
                        checkNullItem(vegetable.findById(menu.vegetables)),
                        checkNullItem(meats.findById(menu.meat)),
                        checkNullItem(water.findById(menu.water)),
                        checkNullItem(noodles.findById(menu.noodles)),
                        checkNullItem(other.findById(menu.others)),
                    ),
                    category = "",
                    method = "",
                    time =  menu.timestamp,
                    saved = true
                )
                val intent = DetailActivity.newIntent(
                    context = this,
                    config = config
                )
                startActivity(intent)
            }
            // Add the populated card to the container
            container.addView(menuCardView)
        }
    }

    private fun setupBackbtn(){
        binding.historyBackButton.setOnClickListener {
            setResult(Activity.RESULT_OK) // Send the result
            finish() // Finish the Activity and return the result
        }
    }
}