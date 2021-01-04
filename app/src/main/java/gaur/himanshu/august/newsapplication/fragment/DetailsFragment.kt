package gaur.himanshu.august.newsapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import gaur.himanshu.august.newsapplication.databinding.FragmentDetailsBinding
import gaur.himanshu.august.newsapplication.retrofit.responce.Article


@AndroidEntryPoint

class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val data = requireArguments()["article"] as Article

        binding.articles = data

        Glide.with(binding.root).load(data.urlToImage).into(binding.detailsImage)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

}