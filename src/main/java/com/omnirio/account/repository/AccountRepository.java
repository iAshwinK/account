package com.omnirio.account.repository;

import com.omnirio.account.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity,String> {
}
