package com.example.myapplication

class Tiempo(daily : Daily){

    var daily : Daily? =null

    init{
        this.daily = daily
    }

}

class Daily(summary:String,icon:String,data:ArrayList<Data>){

    var summary :  String =""
    var icon :String =""
    var data : ArrayList<Data>?=null

    init{
        this.summary = summary

        this.icon = icon

        this.data = data
    }

}

class Data(time:Long,summary:String,icon:String,temperatureHigh:Double){

    var time : Long?=null
    var summary:String =""
    var icon:String =""
    var temperatureHigh :Double?=null

    init{
        this.time = time

        this.summary = summary

        this.icon = icon

        this.temperatureHigh = temperatureHigh

    }

}