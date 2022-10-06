package com.android.autelsdk.album.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.android.autelsdk.R
import com.android.autelsdk.album.AlbumViewModel
import com.android.autelsdk.databinding.FragmentInterfaceDebuggingAlbumBinding
import com.android.autelsdk.databinding.FragmentInterfaceDebuggingRcBinding
import com.android.autelsdk.event.ProductConnectEvent
import com.android.autelsdk.remoteController.RemoteControllerViewModel
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.Utils
import com.autel.internal.album.Album10
import com.autel.internal.album.Album20
import com.autel.internal.remotecontroller.RemoteController10
import com.autel.internal.remotecontroller.RemoteController20
import com.autel.sdk.album.AutelAlbum
import com.autel.sdk.remotecontroller.AutelRemoteController
import org.greenrobot.eventbus.EventBus

class InterfaceDebuggingAlbumFragment : Fragment() {

    private lateinit var binding: FragmentInterfaceDebuggingAlbumBinding

    companion object {
        fun newInstance() = InterfaceDebuggingAlbumFragment()
    }

    private val viewModel: AlbumViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_interface_debugging_album , container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSpinnerItems()
        handleListeners()
    }

    private fun handleListeners() {

        binding.chooseAlbum.viewBtn.setOnClickListener {
            binding.chooseAlbum.showResponseText.visibility = View.VISIBLE
            binding.chooseAlbum.extraOptionParent.visibility = View.GONE
            binding.chooseAlbum.showResponseText.setText("Currently set to ${getCurrentAlbumControllerByName(viewModel.getController())}")
        }

        binding.chooseAlbum.setBtn.setOnClickListener {
            binding.chooseAlbum.showResponseText.visibility = View.GONE
            binding.showResponseText.visibility = View.GONE
            binding.chooseAlbum.extraOptionParent.visibility = View.VISIBLE
        }
        
        binding.chooseAlbum.extraOption.setOnClickListener {
            val controller = setCurrentAlbumControllerByName(binding.chooseAlbum.extraSpinner.selectedItem.toString())
            viewModel.setController(controller)
            binding.chooseAlbum.showResponseText.visibility = View.VISIBLE
            binding.chooseAlbum.showResponseText.setText("Currently set to ${getCurrentAlbumControllerByName(viewModel.getController())}")
        }

        binding.connectDevice.setOnClickListener {
            binding.showResponseText.visibility = View.VISIBLE
            binding.showResponseText.setText("")
            binding.showResponseText.setText("Trying to connect.Waiting For Response...")
            EventBus.getDefault().post(ProductConnectEvent())
        }

        viewModel.getCurrentProduct().observe(viewLifecycleOwner, Observer { product ->
            binding.showResponseText.visibility = View.VISIBLE
            if (product == null) {
                binding.showResponseText.setText(Utils.getColoredText("Product is Not Connected", Constants.FAILED))
            } else {
                binding.showResponseText.setText(Utils.getColoredText("Product Connected Successfully. Type = ${product.type.name}", Constants.SUCCESS))
            }
        })

    }

    private fun getCurrentAlbumControllerByName(controller : AutelAlbum) : String {
        when(controller) {
            is Album10 -> {
                return Constants.Album10
            }
            is Album20 -> {
                return Constants.Album20
            }
        }
        return ""
    }

    private fun setCurrentAlbumControllerByName(name : String) : AutelAlbum {
        when(name) {
            Constants.Album10 -> {
                return Album10()
            }
            Constants.Album20 -> {
                return Album20()
            }
        }
        return viewModel.getController()
    }

    private fun setSpinnerItems() {

        var spinnerAdapter = ArrayAdapter.createFromResource(
            requireActivity().baseContext,
            R.array.album_controllers,
            android.R.layout.simple_spinner_item
        )
        binding.chooseAlbum.extraSpinner.adapter = spinnerAdapter

        val index = context?.resources?.getStringArray(R.array.album_controllers)?.indexOf(getCurrentAlbumControllerByName(viewModel.getController()))

        index?.let { index
            binding.chooseAlbum.extraSpinner.setSelection(index)
        }
    }

}