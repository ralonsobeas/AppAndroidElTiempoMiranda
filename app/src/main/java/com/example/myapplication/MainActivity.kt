package com.example.myapplication

import android.net.Uri
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.Fragments.FragmentWeather
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_fragment_weather.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity(),FragmentWeather.OnFragmentInteractionListener {

    private var tiempo :Tiempo?=null
    private var tamanio :Int = 8
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {


        //Saber si tenemos conexion a red
        if (Network.hayRed(this)) {
            //ejecutar solicitud HTTP  APIkey: bb4b6aee69d806726f418a9cfcf2bfdf
            Toast.makeText(this, "Hay red", Toast.LENGTH_SHORT).show()
            solicitudHTTPVolley("https://api.darksky.net/forecast/bb4b6aee69d806726f418a9cfcf2bfdf/42.6856,-2.9478?exclude=currently,minutely,hourly,alerts,flags&lang=es&units=si")
        } else {
            //mostrar mensaje error
            Toast.makeText(this, "No hay red", Toast.LENGTH_SHORT).show()
        }


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter



    }

    private fun solicitudHTTPVolley(url: String) {
        val queue = Volley.newRequestQueue(this)

        val solicitud = StringRequest(Request.Method.GET, url, Response.Listener<String> { response ->
            try {
                Log.d("solicitudHTTPVolley", response)

                val gson = Gson()
                tiempo = gson.fromJson(response, Tiempo::class.java)

                tamanio = tiempo?.daily?.data!!.size
            } catch (e: Exception) {

            }
        }, Response.ErrorListener { })

        queue.add(solicitud)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            var time = tiempo?.daily?.data!![position].time

            var newtime = java.text.SimpleDateFormat("dd/MM/yyyy").format(java.util.Date(time!! * 1000 ))



            var fra =  FragmentWeather.newInstance(tiempo?.daily?.data!![position].summary,tiempo?.daily?.data!![position].temperatureHigh.toString(),tiempo?.daily?.data!![position].icon,newtime)
            Log.d("ICONO",tiempo?.daily?.data!![position].icon)

            return fra
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return tamanio
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class PlaceholderFragment : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val rootView = inflater.inflate(R.layout.fragment_main, container, false)
            rootView.section_label.text = getString(R.string.section_format, arguments?.getInt(ARG_SECTION_NUMBER))
            return rootView
        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }
}
