package com.spotmap.spotmapandroid.Screens.Account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.spotmap.spotmapandroid.Commons.BasicSpacer
import com.spotmap.spotmapandroid.R
import com.spotmap.spotmapandroid.Screens.Account.Views.SignInView
import com.spotmap.spotmapandroid.Screens.Account.Views.SignUpView
import com.spotmap.spotmapandroid.Screens.Account.Views.goToAccountView

@Composable
fun AccountScreen(navController: NavController,
                  modifier: Modifier = Modifier,
                  viewModel: AccountScreenViewModel) {

    val displayedView = viewModel.displayedView.observeAsState(AccountScreenViewModel.DisplayedView.SIGNUP).value

    LaunchedEffect(displayedView) {
        if (displayedView == AccountScreenViewModel.DisplayedView.LOGGED) {
           navController.navigate("userDetails")
        }
    }

    Column(
        modifier = modifier.background(colorResource(id = R.color.BackgroundColor))
            .fillMaxSize()
            .padding(16.dp)
            .background(color = colorResource(id = R.color.SecondaryColor),
                shape = RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = modifier.fillMaxWidth()
        ) {
            Spacer(modifier = modifier.weight(1f))
            TextButton(onClick = {

            }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = "logo app",
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.LightDarker2Color)) )
            }
        }

        if (displayedView == AccountScreenViewModel.DisplayedView.LOADING) {
            Column(modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                CircularProgressIndicator(color = colorResource(id= R.color.PrimaryColor), modifier = modifier)
            }
        }

        AccountMiddleView(
            displayedView = displayedView,
            modifier = modifier.weight(1f),
            tryToSignUp = { email, password, username ->
                viewModel.tryToSignUp(email,password,username)
            }, tryToSignIn = { email, password ->
                viewModel.tryToSignIn(email,password)
            }
        )

        AccountBottomView(
            displayedView = displayedView,
            modifier = modifier
                .fillMaxWidth(),
            goTo = { displayedView ->
                viewModel.setDisplayedView(displayedView)
            }
        )
    }
}


@Composable
fun AccountMiddleView(displayedView: AccountScreenViewModel.DisplayedView,
                      modifier: Modifier,
                      tryToSignUp: (email: String, password: String, username: String) -> Unit,
                      tryToSignIn: (email: String, password: String) -> Unit) {

    Column(modifier = modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        if (displayedView == AccountScreenViewModel.DisplayedView.SIGNUP || displayedView == AccountScreenViewModel.DisplayedView.SIGNIN) {
            Image(
                painter = painterResource(id = R.drawable.logo_app),
                contentDescription = "logo app",
                modifier = Modifier.fillMaxWidth()
            )
            BasicSpacer()
        }

        when (displayedView) {
            AccountScreenViewModel.DisplayedView.SIGNUP ->
                SignUpView(tryToSignUp = { email, password, username ->
                    tryToSignUp(email, password, username)
                })
            AccountScreenViewModel.DisplayedView.SIGNIN ->
                SignInView(
                    forgotPasswordButtonTapped = {},
                    tryToSignIn = { email, password ->
                        tryToSignIn(email, password)
                    })
            else -> {}
        }
    }
}

@Composable
fun AccountBottomView(displayedView: AccountScreenViewModel.DisplayedView,
                      modifier: Modifier,
                      goTo: (AccountScreenViewModel.DisplayedView) -> Unit) {

    when (displayedView) {
        AccountScreenViewModel.DisplayedView.SIGNUP ->
            goToAccountView(modifier,"Sign in","Already have an account?", goToButtonTapped = {
                goTo(AccountScreenViewModel.DisplayedView.SIGNIN)
            })
        AccountScreenViewModel.DisplayedView.SIGNIN ->
            goToAccountView(modifier,"Sign up", "Don't have an account?", goToButtonTapped = {
                goTo(AccountScreenViewModel.DisplayedView.SIGNUP)
            })
        else -> {}
    }
}
