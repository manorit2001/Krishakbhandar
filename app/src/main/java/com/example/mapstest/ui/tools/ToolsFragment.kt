package com.example.mapstest.ui.tools


import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mapstest.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_tools.*
import com.tbruyelle.rxpermissions2.RxPermissions
import android.Manifest.permission






class ToolsFragment : Fragment() {

    private var disposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.example.mapstest.R.layout.fragment_tools, container, false)
    }

    override fun onStart() {
        super.onStart()

        val rxPermissions = RxPermissions(this)
        rxPermissions
                .request(Manifest.permission.CAMERA)
                .subscribe { granted ->
                    if (granted!!) { // Always true pre-M
                        // I can control the camera now
                        disposable = barcodeView
                                .drawOverlay()
                                .getObservable()
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        {
                                            Toast.makeText(requireContext(), it.displayValue + " - Suceesfully Scanned QR Code", Toast.LENGTH_SHORT).show()
                                            activity?.onBackPressed()
                                        },
                                        {
                                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        })
                    } else {
                        activity?.onBackPressed();
                    }
                }

    }

    override fun onStop() {
        disposable?.dispose()

        super.onStop()
    }

}