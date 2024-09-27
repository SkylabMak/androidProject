package com.example.androidproject

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.ComponentActivity
import com.example.androidproject.database.*
import com.example.androidproject.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {
    private var conflictMain = false
    private var conflictCat = false
    private var conflictMeth = false
    private lateinit var binding: ActivityMainBinding
    //on click
    private lateinit var other : OtherIngredientsCSVStorage
    private lateinit var noodles :NoodlesCSVHandler
    private lateinit var water : WaterCSVStorage
    //on create
    private lateinit var menu: MenuStorage
    private lateinit var meats: MeatCSVStorage
    private lateinit var vegetable: VegetableCSVStorage
    private lateinit var cookingMethod: CookingMethodCSVStorage
    private lateinit var category: CategoryCSVStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        menu = MenuStorage(this)
        meats = MeatCSVStorage(this)
        vegetable = VegetableCSVStorage(this)
        cookingMethod = CookingMethodCSVStorage(this)
        category = CategoryCSVStorage(this)

        //setup
        setupConflictSelect()
        setupSpinners()
        setupRandomBtn()
    }

    private fun setupRandomBtn() {
        binding.randomButton.setOnClickListener {
            Log.d("RandomButton", "Random button clicked!")

            // ตรวจสอบและเริ่มต้นค่าให้ noodles และ other หากยังไม่ได้เริ่มต้น
            if (!::noodles.isInitialized) {
                noodles = NoodlesCSVHandler(this)
            }
            if (!::other.isInitialized) {
                other = OtherIngredientsCSVStorage(this)
            }
            if (!::water.isInitialized) {
                water = WaterCSVStorage(this)
            }

            var result: String = ""
            val catID = binding.categorySelect.selectedItemPosition - 1
            val cookID = binding.categorySelect.selectedItemPosition - 1
            val randomMenu: Menu = menu.randomMenu(
                catID.toString(),
                cookID.toString()
            )!!

            result += " " + randomMenu.name
            result += if (randomMenu.hasVegetables) " " + vegetable.findRandom()!!.name else ""
            result += if (randomMenu.hasMeat) " " + meats.findRandom()!!.name else ""
            result += if (randomMenu.hasWater) " " + water.findRandom()!!.name else ""
            result += if (randomMenu.hasNoodles) " " + noodles.findRandom()!!.name else ""
            result += if (randomMenu.hasOthers) " " + other.findRandom()!!.name else ""
            Log.d("food result: ", result)
        }
    }


    private fun setupConflictSelect() {
        binding.menuSelect.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                Log.d("conflictArises", "menuSelect $conflictMain $conflictCat $conflictMeth")
                if (!conflictMain) {
                    if(binding.categorySelect.selectedItemPosition != 0){
                        conflictCat = true
                    }
                    if(binding.methodSelect.selectedItemPosition != 0){
                        conflictMeth = true
                    }
                    Log.d("conflictArises", "run menuSelect $conflictMain $conflictCat $conflictMeth")
                    binding.categorySelect.setSelection(0)
                    binding.methodSelect.setSelection(0)
                    Log.d("conflictArises", "after run menuSelect $conflictMain $conflictCat $conflictMeth")
                }
                else{
                    conflictMain = false
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        binding.categorySelect.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                Log.d("conflictArises", "categorySelect $conflictMain $conflictCat $conflictMeth")
                if (!conflictCat && binding.menuSelect.selectedItemPosition != 0) {
                    conflictMain = true
                    binding.menuSelect.setSelection(0)
                }
                else{
                    conflictCat = false
                }
                Log.d("conflictArises", "after categorySelect $conflictMain $conflictCat $conflictMeth")
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        binding.methodSelect.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                Log.d("conflictArises", "methodSelect $conflictMain $conflictCat $conflictMeth")
                if (!conflictMeth&&binding.menuSelect.selectedItemPosition != 0) {
                    conflictMain = true
                    binding.menuSelect.setSelection(0)
                }
                else{
                    conflictMeth = false
                }
                Log.d("conflictArises", "after methodSelect $conflictMain $conflictCat $conflictMeth")
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }

    private fun setupSpinners() {
        setupSpinner(binding.categorySelect, category.findAll())
        setupSpinner(binding.methodSelect, cookingMethod.findAll())
        setupSpinner(binding.menuSelect, menu.findAll().map { Item(it.id,it.name) })
        setupSpinner(binding.vegetablePrevent, vegetable.findAll())
        setupSpinner(binding.meatPrevent, meats.findAll())
    }

    private fun <T : Item> setupSpinner(spinner: Spinner, items: List<T>) {
        // Map items to their name property
        val itemNames = items.map { it.name }.toMutableList()

        // Add the first option "ไม่เลือก" at the beginning
        itemNames.add(0, "ไม่เลือก")

        // Setup the adapter with itemNames
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, itemNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Set the selection to the first item ("ไม่เลือก")
//        spinner.setSelection(0)
    }

}
