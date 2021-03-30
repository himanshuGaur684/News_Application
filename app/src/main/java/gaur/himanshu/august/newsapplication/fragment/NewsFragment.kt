package gaur.himanshu.august.newsapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import dagger.hilt.android.AndroidEntryPoint
import gaur.himanshu.august.newsapplication.NewsViewModel
import gaur.himanshu.august.newsapplication.R
import gaur.himanshu.august.newsapplication.aapters.AdapterClicklListioners
import gaur.himanshu.august.newsapplication.aapters.NewsPagingAdapter
import gaur.himanshu.august.newsapplication.retrofit.responce.Article
import kotlinx.android.synthetic.main.fragment_news.view.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class NewsFragment : Fragment(), AdapterClicklListioners {


    private val viewModel by viewModels<NewsViewModel>()

    private val newsPagingAdapter = NewsPagingAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // Paging 3 PagingSource<Int,value>

//        viewModel.list.observe(viewLifecycleOwner) {
//            newsPagingAdapter.submitData(lifecycle, it)
//        }
//
//        newsPagingAdapter.addLoadStateListener { state ->
//
//            when (state.refresh) {
//                is LoadState.Loading -> {
//                    view.news_progress.visibility = View.VISIBLE
//                }
//                is LoadState.NotLoading -> {
//                    view.news_progress.visibility = View.GONE
//                }
//                is LoadState.Error -> {
//                    view.news_progress.visibility = View.GONE
//                    Toast.makeText(requireContext(), "Error Occured", Toast.LENGTH_SHORT).show()
//                }
//
//            }
//
//        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pager.collectLatest {
                newsPagingAdapter.submitData(it)
            }
        }


        view.news_recycler.adapter = newsPagingAdapter


    }

    override fun clickListioners(article: Article) {
        findNavController().navigate(
            R.id.action_newsFragment_to_detailsFragment,
            bundleOf("article" to article)
        )
    }

}