package com.nyererefy.data.repositories

import com.apollographql.apollo.ApolloClient
import com.nyererefy.BuildConfig.GITHUB_GRAPHQL_URL
import com.nyererefy.BuildConfig.GITHUB_TOKEN
import com.nyererefy.github.graphql.CollaboratorsQuery
import com.nyererefy.utilities.Resource
import com.nyererefy.utilities.common.invokeQuery
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Used to fetch all contributors. If your commit is merged your profile will be fetched too.
 *
 * Solution to downloading schema.json issue with authorization header.
 * https://github.com/apollographql/apollo-tooling/issues/1325#issuecomment-508005507
 */
class GithubRepository {
    fun fetchContributors(name: String): Resource<CollaboratorsQuery.Data> {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient
                .Builder()
                .addInterceptor(logger)
                .addInterceptor {
                    val req = it.request()
                    val session = req.newBuilder()

                    session.addHeader("Authorization", "Bearer $GITHUB_TOKEN")
                    it.proceed(session.build())
                }
                .build()

        val client = ApolloClient.builder()
                .serverUrl(GITHUB_GRAPHQL_URL)
                .okHttpClient(okHttpClient)
                .build()

        val query = CollaboratorsQuery.builder().name(name).build()

        return invokeQuery<CollaboratorsQuery.Data>(client.query(query))
    }
}