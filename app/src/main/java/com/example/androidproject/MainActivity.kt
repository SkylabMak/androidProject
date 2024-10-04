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
import java.util.concurrent.atomic.AtomicInteger

class MainActivity : ComponentActivity() {
    private var conflictMain = false
    private var conflictCat = false
    private var conflictMeth = false
    private lateinit var binding: ActivityMainBinding

    //on click
    private lateinit var other: OtherIngredientsCSVStorage
    private lateinit var noodles: NoodlesCSVHandler
    private lateinit var water: WaterCSVStorage

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
        binding.resultCalDisplay.text = getString(R.string.cal_value, 0)
        setupConflictSelect()
        setupSpinners()
        setupRandomBtn()
    }

    private fun initializeIngredients() {
        if (!::noodles.isInitialized) {
            noodles = NoodlesCSVHandler(this)
        }
        if (!::other.isInitialized) {
            other = OtherIngredientsCSVStorage(this)
        }
        if (!::water.isInitialized) {
            water = WaterCSVStorage(this)
        }
    }

    private fun getRandomMenu(
        menuID: Int,
        catID: Int,
        cookID: Int,
        vegetableID: Int,
        meatID: Int,
    ): Menu? {
        return if (menuID == 0) {
            Log.d("catID cookID", "$catID$cookID")
            menu.randomMenu(
                catID.toString(),
                cookID.toString(),
                vegetableID.toString(),
                meatID.toString()
            )
        } else {
            Log.d("menuID", menuID.toString())
            menu.getById(menuID.toString())
        }
    }

    private fun setupRandomBtn() {
        binding.randomButton.setOnClickListener {
            Log.d("RandomButton", "Random button clicked!")
            initializeIngredients()

            val result = StringBuilder()
            val totalCalories = AtomicInteger(0)
            val catID = binding.categorySelect.selectedItemPosition
            val cookID = binding.methodSelect.selectedItemPosition
            val menuID = binding.menuSelect.selectedItemPosition
            val vegetableID = binding.vegetablePrevent.selectedItemPosition
            val meatID = binding.meatPrevent.selectedItemPosition

            val randomMenu: Menu? = getRandomMenu(menuID, catID, cookID, vegetableID, meatID)

            if (randomMenu == null) {
                Log.d("food random ", "no food random")
                binding.resultNameDisplay.text = getString(R.string.NoInfoNow)
                return@setOnClickListener
            }
            Log.d("food random ", "food random found")
            totalCalories.addAndGet(randomMenu.cal)
            result.append(" " + randomMenu.name)

            addIngredientToResult(randomMenu.hasVegetables, vegetableID, vegetable, result, totalCalories)
            addIngredientToResult(randomMenu.hasMeat, meatID, meats, result, totalCalories)
            addIngredientToResult(randomMenu.hasWater, 0, water, result, totalCalories)
            addIngredientToResult(randomMenu.hasNoodles, 0, noodles, result, totalCalories)
            addIngredientToResult(randomMenu.hasOthers, 0, other, result, totalCalories)
            binding.resultNameDisplay.text = result.toString()
            binding.resultCalDisplay.text = getString(R.string.cal_value, totalCalories.get())
        }
    }

    private fun <T : Item> addIngredientToResult(
        hasIngredient: Boolean,
        ingredientID: Int,
        item: CSVHandlerItem<T>,
        result: StringBuilder,
        resultCal: AtomicInteger,
    ) {
        if (hasIngredient) {
            val ingredient: T? = if (ingredientID != 0) {
                item.findRandom(ingredientID.toString())  // ใช้ findRandomExclude เพื่อข้าม ingredient ที่มี id ตรงกับที่ส่งเข้ามา
            } else {
                item.findRandom()  // ใช้ findRandom เพื่อสุ่มเลือก
            }

            if (ingredient != null) {
                result.append(" " + ingredient.name)  // ใช้ append() เพื่อเพิ่มข้อความเข้าไปใน StringBuilder
                resultCal.addAndGet(ingredient.cal)  // เพิ่มแคลอรี่เข้าไปใน resultCal
            }
        }
    }


    private fun setupConflictSelect() {
        binding.menuSelect.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long,
            ) {
                Log.d("conflictArises", "menuSelect $conflictMain $conflictCat $conflictMeth")
                if (!conflictMain) {
                    if (binding.categorySelect.selectedItemPosition != 0) {
                        conflictCat = true
                    }
                    if (binding.methodSelect.selectedItemPosition != 0) {
                        conflictMeth = true
                    }
                    Log.d(
                        "conflictArises",
                        "run menuSelect $conflictMain $conflictCat $conflictMeth"
                    )
                    binding.categorySelect.setSelection(0)
                    binding.methodSelect.setSelection(0)
                    Log.d(
                        "conflictArises",
                        "after run menuSelect $conflictMain $conflictCat $conflictMeth"
                    )
                } else {
                    conflictMain = false
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        binding.categorySelect.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    Log.d(
                        "conflictArises",
                        "categorySelect $conflictMain $conflictCat $conflictMeth"
                    )
                    if (!conflictCat && binding.menuSelect.selectedItemPosition != 0) {
                        conflictMain = true
                        binding.menuSelect.setSelection(0)
                    } else {
                        conflictCat = false
                    }
                    Log.d(
                        "conflictArises",
                        "after categorySelect $conflictMain $conflictCat $conflictMeth"
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Do nothing
                }
            }

        binding.methodSelect.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long,
            ) {
                Log.d("conflictArises", "methodSelect $conflictMain $conflictCat $conflictMeth")
                if (!conflictMeth && binding.menuSelect.selectedItemPosition != 0) {
                    conflictMain = true
                    binding.menuSelect.setSelection(0)
                } else {
                    conflictMeth = false
                }
                Log.d(
                    "conflictArises",
                    "after methodSelect $conflictMain $conflictCat $conflictMeth"
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }

    private fun setupSpinners() {
        setupSpinner(binding.categorySelect, category.findAll())
        setupSpinner(binding.methodSelect, cookingMethod.findAll())
        setupSpinner(binding.menuSelect, menu.findAll().map { Item(it.id, it.name, it.cal) })
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
