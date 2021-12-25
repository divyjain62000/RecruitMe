package com.recruitme.service.domain.address;

import com.recruitme.domain.Address;
import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.service.domain.address.dto.AddressDTO;

public interface AddressService {
    /**
     * Save address.
     *
     * @param addressDTO the address dto
     * @return the integer
     * @throws RecruitmeException the recruitme exception
     */
    Integer save(AddressDTO addressDTO) throws RecruitmeException;

    /**
     * Edit address.
     *
     * @param addressDTO the address dto
     * @return the integer
     * @throws RecruitmeException the recruitme exception
     */
    Integer edit(AddressDTO addressDTO) throws RecruitmeException;

    /**
     * Find by id address dto.
     *
     * @param id the id
     * @return the address dto
     */
    AddressDTO findById(Integer id);

    /**
     * Delete address.
     *
     * @param address the address
     */
    void delete(Address address);
}
