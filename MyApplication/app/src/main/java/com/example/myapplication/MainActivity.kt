package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly

class MainActivity : AppCompatActivity() {

    companion object {
        private val INPUT_BUTTONS = listOf(
                listOf("CE", "", "", "C"),
            listOf("1", "2", "3", "/"),
            listOf("4", "5", "6", "*"),
            listOf("7", "8", "9", "-"),
            listOf("0", ".", "=", "+")
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addCells(findViewById(R.id.calculator_input_container_line1), 0)
        addCells(findViewById(R.id.calculator_input_container_line2), 1)
        addCells(findViewById(R.id.calculator_input_container_line3), 2)
        addCells(findViewById(R.id.calculator_input_container_line4), 3)
        addCells(findViewById(R.id.calculator_input_container_line5), 4)

    }

    private fun addCells(linearLayout: LinearLayout, position: Int) {
        for (x in INPUT_BUTTONS[position].indices) {
            linearLayout.addView(
                    TextView(
                            ContextThemeWrapper(this, R.style.CalculatorInputButton)
                    ).apply {
                        text = INPUT_BUTTONS[position][x]
                        setOnClickListener { onCellClicked(this.text.toString()) }
                    },
                    LinearLayout.LayoutParams(
                            0,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            1f
                    )
            )
        }
    }


    private var input: Float? = null
    private var previousInput: Float? = null
    // private var intermediate: Float? = null
    private var memory: Boolean? = false
    private var symbol: String? = null

    private fun onCellClicked(value: String) {
        when {

                value.isNum() -> {
                    input = value.toFloat()
                    updateDisplayContainer(value)
                }

            // value == "." ->  onDoteClicked()
            value == "=" -> onEqualsClicked()
            value == "CE" -> onCeClicked()
            value == "C" -> onCClicked()
            listOf("/", "*", "-", "+",".").contains(value) -> onSymbolClicked(value)
        }
    }

    private fun onCeClicked(){
        input=null
        previousInput=null
        symbol=null
        updateDisplayContainer("")
    }

    private fun onCClicked(){
        input=previousInput
        updateDisplayContainer("")
    }

    private fun onEqualsClicked() {
        Log.d("CELL", previousInput.toString() )
        if (input == null || previousInput == null || symbol == null) {
            Log.d("CELL", "0.1" )
            return
        }
        Log.d("CELL", "1" )

        updateDisplayContainer(when (symbol) {
            "+" -> input!! + previousInput!!
            "-" ->  previousInput!! - input!!
            "*" -> input!! * previousInput!!
            "/" -> when(input){
                0.0.toFloat() -> "ERROR"
                else -> previousInput!! / input!!
            }
            //"." ->

            else -> "ERROR"
        })
        Log.d("CELL", "2" )

         when (symbol) {
            "+" -> previousInput=input!! + previousInput!!
            "-" ->  previousInput=previousInput!! - input!!
            "*" -> previousInput=input!! * previousInput!!
            "/" -> when(input){
                0.0.toFloat() -> "ERROR"
                else -> previousInput=previousInput!! / input!!
            }
            //"." ->

            else -> "ERROR"
        }
        memory=true
        Log.d("CELL", previousInput.toString() )
        input = null
        symbol = null
    }

    // private fun floatNum(float: Float){
    //     intermediate=previousInput!!+float/10
    //     input=intermediate
    //     //return this.float
    // }

    // private fun onDoteClicked() {
    //     // Log.d("CELL", "1" )
    //     previousInput = input
    //     // onSymbolClicked(".")
    //     // Log.d("CELL", "2" )
    //     if (input == null ) {
    //         updateDisplayContainer("previous")
    //         return
    //     }
    //     // Log.d("CELL", "3" )
    //     /*f (previousInput==null){
    //         Log.d("CELL", "4" )
    //         updateDisplayContainer("previousInput!!")
    //         return
    //     }*/
    //     intermediate=previousInput
    //     updateDisplayContainer(intermediate!!)
    //     // Log.d("CELL", "5" )

    //     // updateDisplayContainer("input!!")
    //     // Log.d("CELL", "6" )
    //     when(input){
    //         null -> intermediate=previousInput
    //     }
    //     intermediate=previousInput!!+input!!/10
    //     // Log.d("CELL", "7" )
    //     /*updateDisplayContainer(intermediate!!)*/

    //     previousInput=intermediate
    //     // Log.d("CELL", "8" )

    // }

    private fun onSymbolClicked(symbol: String) {
        if (memory==true){
            this.symbol = symbol
            input = null
        }
        else{
            this.symbol = symbol
            previousInput = input
            input = null
        }

    }

    private fun updateDisplayContainer(value: Any) {
        findViewById<TextView>(R.id.calculator_display_container).text = value.toString()
    }


}
fun String.isNum(): Boolean {
    return length == 1 && isDigitsOnly()
}


