/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cf.pivotal.accountClient;

import java.util.List;

import org.springframework.stereotype.Repository;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface AccountProfileRepository {

    @RequestLine("GET /profiles/?userId={userId}&passwd={passwd}")
    public List<Accountprofile> findByUseridAndPasswd(
            @Param(value = "userId") String userId,
            @Param(value = "passwd") String passwd);

    @RequestLine("GET /profiles/?userId={userId}")
    public List<Accountprofile> findByUserid(
            @Param(value = "userId") String userId);

    @RequestLine("GET /profiles/?authToken={authtoken}")
    public List<Accountprofile> findByAuthtoken(
            @Param(value = "authtoken") String authtoken);

    @RequestLine("GET /profiles/{id}")
    public Accountprofile findOne(@Param(value = "id") Long id);

    @RequestLine("GET /profiles/{id}/accounts")
    public List<Account> findAccounts(@Param(value = "id") Long id);

    @RequestLine("POST /profiles/")
    @Headers("Content-Type: application/json")
    public Accountprofile save(@RequestBody Accountprofile profile);

    @RequestLine("DELETE /profiles/")
    @Headers("Content-Type: application/json")
    public Accountprofile delete(@RequestBody Accountprofile profile);
}
