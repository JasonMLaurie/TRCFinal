package com.example.tripplanner.view.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.tripplanner.Controller.bll.TripPlannerLogic
import com.example.tripplanner.R
import com.example.tripplanner.databinding.FragmentDetayBinding
import com.example.tripplanner.model.ResimEntity
import com.example.tripplanner.model.YerEntity
import com.example.tripplanner.model.ZiyaretEntity
import com.example.tripplanner.view.activities.MapsActivity
import com.example.tripplanner.view.adapters.CustomPagerAdapter
import com.example.tripplanner.view.adapters.ziyaret.ZiyaretAdapter

/** Gezilecek Yer Detay Fragment */
class DetayFragment : Fragment(), ViewPager.OnPageChangeListener, View.OnClickListener  {


    private lateinit var binding : FragmentDetayBinding
    private lateinit var yerObject : YerEntity
    private var yerResimListe=ArrayList<ResimEntity>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetayBinding.inflate(inflater, container, false)

        setInitialViews()
        clickListeners()
        viewPagerImageSlider()

        return binding.root
    }


    /** Fill views with default values */
    @SuppressLint("SetTextI18n")
    fun setInitialViews(){
        val bundle : DetayFragmentArgs by navArgs()
        yerObject = bundle.yerObject

        binding.apply {
            tvYerKisaTanimBilgi.text = yerObject.kisaTanim
            tvKisaAciklamaBilgisi.text = yerObject.kisaAciklama

            //Oncelik için kontrol
            if (yerObject.oncelik.equals("oncelik1"))//yeşil
            { ivOncelikD.setImageResource(R.drawable.oncelik1_sekil) }
            else if (yerObject.oncelik.equals("oncelik2"))//mavi
            { ivOncelikD.setImageResource(R.drawable.oncelik2_sekil) }
            else if (yerObject.oncelik.equals("oncelik3"))//gri
            { ivOncelikD.setImageResource(R.drawable.oncelik3_sekil)}

            //TODO yer fotoğrafna göre tarih


        }


        val ziyaretList = TripPlannerLogic.ziyaretleriGetir(yerEntity = yerObject, context = requireContext())
        ziyaretControl(ziyaretList)
        if(!ziyaretList.isNullOrEmpty()){
            val adapter = ZiyaretAdapter(requireContext(),ziyaretList)
            binding.rvZiyaretGecmisi.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            binding.rvZiyaretGecmisi.adapter = adapter
        }

        Log.e("Logcat", TripPlannerLogic.tumZiyaretleriGetir(requireContext()).size.toString())
        Log.e("Logcat",TripPlannerLogic.tumZiyaretleriGetir(requireContext()).toString())



    }

    private fun ziyaretControl(ziyaretList : ArrayList<ZiyaretEntity>) {
        if(!ziyaretList.isNullOrEmpty()){
            yerObject.ziyaretEdildi = 1
            TripPlannerLogic.yerGuncelle(requireContext(), yerObject)
        }
    }

    /** Click Listeners*/
    fun clickListeners(){

        binding.btnKonumGoster.setOnClickListener {
            val intent = Intent(requireContext(), MapsActivity::class.java)
            intent.putExtra("Latitude",yerObject.latitude)
            intent.putExtra("Longitude",yerObject.longitude)
            intent.putExtra("mode",true)
            startActivity(intent)
        }

        binding.btnZiyaretEkle.setOnClickListener {
            val detay2ZiyaretEkleNavDir = DetayFragmentDirections.actionDetayFragmentToZiyaretEkleFragment(yerObject.id)
            Navigation.findNavController(it).navigate(detay2ZiyaretEkleNavDir)
        }

    }

    /** Refreshing views in case of any visit being added */
    override fun onResume() {
        super.onResume()
        setInitialViews()
    }

    /*
    val foodList2DetailNavDir = FoodListFragmentDirections.foodList2Detail(foodObject)
    Navigation.findNavController(it).navigate(foodList2DetailNavDir)
            */


    //viewPagerImplements
    var yerFotograflari = intArrayOf(R.drawable.tempimage1, R.drawable.tempimage1, R.drawable.tempimage1)
    var yerTarihleri= arrayOf("tarih1","tarih2","tarih3")


    var mViewPager: ViewPager? = null
    private var mAdapter: CustomPagerAdapter? = null
    private var pager_indicator: LinearLayout? = null
    private var dotsCount = 0
    private lateinit var dots: Array<ImageView?>


    /** ViewPagerSlide operations */
    fun viewPagerImageSlider(){

        mViewPager = binding.viewpager
        pager_indicator = binding.viewPagerCountDots

        mAdapter = CustomPagerAdapter(requireContext(), yerFotograflari,yerTarihleri)

        mViewPager!!.adapter = mAdapter
        mViewPager!!.currentItem = 0
        mViewPager!!.setOnPageChangeListener(this)
        setPageViewIndicator()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setPageViewIndicator() {
        Log.d("###setPageViewIndicator", " : called")
        dotsCount = mAdapter!!.count
        dots = arrayOfNulls(dotsCount)
        for (i in 0 until dotsCount) {
            dots[i] = ImageView(requireContext())
            dots[i]!!.setImageResource(R.drawable.unselected_dot_slider)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(4, 0, 4, 0)
            dots[i]!!.setOnTouchListener { v, event ->
                mViewPager!!.currentItem = i
                true
            }
            pager_indicator!!.addView(dots[i], params)
        }
        dots[0]!!.setImageResource(R.drawable.selected_dot_slider)
    }

    override fun onPageSelected(position: Int) {
        Log.d("###onPageSelected, pos ", position.toString())
        for (i in 0 until dotsCount) {
            dots[i]!!.setImageResource(R.drawable.unselected_dot_slider)
        }
        dots[position]!!.setImageResource(R.drawable.selected_dot_slider)
        if (position + 1 == dotsCount) {
        } else { }
    }

    override fun onClick(v: View) {
        //TODO resim tam ekran açılacak
    }
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
    override fun onPageScrollStateChanged(state: Int) {}


}