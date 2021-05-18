package com.arga.jetpack.submission2.presentation

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.arga.jetpack.submission2.R
import com.arga.jetpack.submission2.presentation.activity.MainActivity
import com.arga.jetpack.submission2.util.DummyData
import com.arga.jetpack.submission2.util.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    private val dummyMovie = DummyData.generateDummyMovies()
    private val dummyTvShow = DummyData.generateDummyTvShows()

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoIdlingResource())
    }
    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoIdlingResource())
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                        dummyMovie.size
                )
        )
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.rv_movies)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        0,
                        click()
                )
        )
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(withText(dummyMovie[0].title)))
    }

    @Test
    fun loadTvShows() {
        onView(withText("TV SHOW")).perform(click())
        onView(withId(R.id.rv_tvshow)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvshow)).perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                        dummyTvShow.size
                )
        )
    }

    @Test
    fun loadDetailTvShow() {
        onView(withText("TV SHOW")).perform(click())
        onView(withId(R.id.rv_tvshow)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        0,
                        click()
                )
        )
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(withText(dummyTvShow[0].name)))
    }

    @Test
    fun checkViewPagerIsDisplayed() {
        onView(withId(R.id.view_pager)).check(matches(isDisplayed()))
    }

    @Test
    fun checkTabLayoutDisplayed() {
        onView(withId(R.id.tabs))
                .perform(click())
                .check(matches(isDisplayed()))
    }
}