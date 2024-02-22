package com.ud.mycalculator_xml

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var tvInput:TextView?=null
    var lastNumeric:Boolean=false
    var lastDot:Boolean=false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput=findViewById(R.id.tvInput)
    }

    fun onDigit(view:View){
        tvInput?.append((view as Button).text)
        lastNumeric=true
    }
    fun onClear(view :View){
        tvInput?.text=""
        lastDot=false
        lastNumeric=false
    }
    fun onDecimalPoint(view:View){
        if(lastNumeric&&!lastDot){
            tvInput?.append(".")
            lastDot=true
            lastNumeric=false
        }
    }

    @SuppressLint("SetTextI18n")
    fun onEqual(view:View){
        if(lastNumeric){
            var tvValue =tvInput?.text.toString()
            var prefix=""
            try {
                if(tvValue.startsWith("-")){
                    prefix="-"
                    tvValue=tvValue.substring(1)
                }
                if(tvValue.contains("-")){
                    val splitValue =tvValue.split("-")

                    var a=splitValue[0]
                    var b=splitValue[1]

                    if(prefix.isNotEmpty()){
                        a=prefix+a
                    }
                    tvInput?.text=removeZeroAfterDot((a.toDouble() - b.toDouble()).toString())
                }else if(tvValue.contains("+")){
                    val splitValue =tvValue.split("+")

                    var a=splitValue[0]
                    var b=splitValue[1]

                    if(prefix.isNotEmpty()){
                        a=prefix+a
                    }
                    tvInput?.text=removeZeroAfterDot((a.toDouble() + b.toDouble()).toString())
                }else if(tvValue.contains("/")){
                        val splitValue =tvValue.split("/")

                        var a=splitValue[0]
                        var b=splitValue[1]

                        if(prefix.isNotEmpty()){
                            a=prefix+a
                        }
                        tvInput?.text=removeZeroAfterDot((a.toDouble() / b.toDouble()).toString())
                }else if(tvValue.contains("*")){
                    val splitValue =tvValue.split("*")

                    var a=splitValue[0]
                    var b=splitValue[1]

                    if(prefix.isNotEmpty()){
                        a=prefix+a
                    }
                    tvInput?.text=removeZeroAfterDot((a.toDouble() * b.toDouble()).toString())
                }
            }catch (e:ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result:String):String{
        var value=result
        if(result.contains(".0"))
            value=result.substring(0,result.length-2)
        return value
    }

    fun onOperator(view:View){
        tvInput?.text?.let {
            if(tvInput?.text==""){
                lastNumeric=true
            }
            if (lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastDot=false
                lastNumeric=false
            }
        }
    }

    private fun isOperatorAdded(value:String):Boolean{
        return if(value.startsWith("-")){
            false
        }else if(tvInput?.text==""){
           return false
        } else{
            value.contains("/")||
                    value.contains("*")||
                    value.contains("-")||
                    value.contains("+")
        }
    }
}