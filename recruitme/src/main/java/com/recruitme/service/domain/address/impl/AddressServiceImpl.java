package com.recruitme.service.domain.address.impl;

import com.recruitme.domain.Address;
import com.recruitme.enums.error.*;
import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.model.CityModel;
import com.recruitme.model.CountryModel;
import com.recruitme.model.StateModel;
import com.recruitme.repository.AddressRepository;
import com.recruitme.service.domain.address.AddressService;
import com.recruitme.service.domain.address.dto.AddressDTO;
import com.recruitme.service.domain.city.dto.CityDTO;
import com.recruitme.service.domain.state.dto.StateDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.analysis.function.Add;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {


    @Autowired
    private AddressRepository addressRepository;

    /**
     *  method to add parents
     * @param addressDTO
     * @throws RecruitmeException
     */
    @Override
    public Integer save(AddressDTO addressDTO) throws RecruitmeException
    {
        RecruitmeException recruitmeException=new RecruitmeException();
        if(addressDTO==null) {
            recruitmeException.addException(ErrorFor.ADDRESS_ERR.getErrorFor(), AddressError.ADDRESS_DETAIL_REQURIED.getAddressError());
            throw recruitmeException;
        }
        if(addressDTO.getId()==null || addressDTO.getId()!=0) addressDTO.setId(0);
        if(addressDTO.getAddressLine1()==null || addressDTO.getAddressLine1().length()==0) {
            recruitmeException.addException(ErrorFor.ADDRESS_LINE1_ERR.getErrorFor(), AddressError.ADDRESS_LINE1_REQUIRED.getAddressError());
        }
        if(addressDTO.getCountryId()==null || addressDTO.getCountryId()==0) {
            recruitmeException.addException(ErrorFor.COUNTRY_ERR.getErrorFor(), CountryError.COUNTRY_REQURED.getCountryError());
        }
        else if(CountryModel.countryIdMap.containsKey(addressDTO.getCountryId())==false) {
            recruitmeException.addException(ErrorFor.COUNTRY_ERR.getErrorFor(), CountryError.INVALID_COUNTRY_ID.getCountryError());
        }
        if(addressDTO.getStateId()==null || addressDTO.getStateId()==0) {
            recruitmeException.addException(ErrorFor.STATE_ERR.getErrorFor(),StateError.STATE_REQURED.getStateError());
        }
        else {
            List<StateDTO> stateList=StateModel.stateCountryIdMap.get(addressDTO.getCountryId());
            Boolean isStateFound=false;
            if(stateList!=null) {
                for (StateDTO state : stateList) {
                    if (state.getId() == addressDTO.getStateId()) {
                        isStateFound = true;
                        break;
                    }
                }
            }
            if(isStateFound==false) recruitmeException.addException(ErrorFor.STATE_ERR.getErrorFor(),StateError.INVALID_STATE_ID.getStateError());
        }

        if(addressDTO.getCityId()==null || addressDTO.getCityId()==0) {
            recruitmeException.addException(ErrorFor.CITY_ERR.getErrorFor(),CityError.CITY_REQUIRED.getCityError());
        }
        else {
            List<CityDTO> cityList=CityModel.cityStateIdMap.get(addressDTO.getStateId());
            Boolean isCityFound=false;
            log.debug("city id: {}",addressDTO.getCityId());
            if(cityList!=null) {
                for (CityDTO city : cityList) {
                    int tmpCityId = city.getId();
                    if (tmpCityId == addressDTO.getCityId()) {
                        isCityFound = true;
                        break;
                    }
                }
            }
            if(isCityFound==false) recruitmeException.addException(ErrorFor.CITY_ERR.getErrorFor(), CityError.INVALID_CITY_ID.getCityError());
        }

        if(addressDTO.getPinCode()==null || addressDTO.getPinCode().length()==0) {
            recruitmeException.addException(ErrorFor.PIN_CODE_ERR.getErrorFor(),AddressError.PIN_CODE_REQUIRED.getAddressError());
        }

        if(recruitmeException.getExceptions().size()>0) throw recruitmeException;
        ModelMapper modelMapper=new ModelMapper();
        Address address=modelMapper.map(addressDTO,Address.class);
        address=this.addressRepository.save(address);
        return address.getId();

    }
    /**
     *  method to edit parents
     * @param addressDTO
     * @throws RecruitmeException
     */
    @Override
    public Integer edit(AddressDTO addressDTO) throws RecruitmeException
    {
        RecruitmeException recruitmeException=new RecruitmeException();
        if(addressDTO==null) {
            recruitmeException.addException(ErrorFor.ADDRESS_ERR.getErrorFor(), AddressError.ADDRESS_DETAIL_REQURIED.getAddressError());
            throw recruitmeException;
        }
        if(addressDTO.getId()==null) {
            recruitmeException.addException(ErrorFor.ADDRESS_ID_ERR.getErrorFor(), CommonError.ID_REQUIRED.getCommonError());
        }else {
            Optional<Address> address=this.addressRepository.findById(addressDTO.getId());
            if(address.isPresent()==false) {
                recruitmeException.addException(ErrorFor.ADDRESS_ID_ERR.getErrorFor(), CommonError.INVALID_ID.getCommonError());
            }
        }
        if(addressDTO.getAddressLine1()==null || addressDTO.getAddressLine1().length()==0) {
            recruitmeException.addException(ErrorFor.ADDRESS_LINE1_ERR.getErrorFor(), AddressError.ADDRESS_LINE1_REQUIRED.getAddressError());
        }
        if(addressDTO.getCountryId()==null || addressDTO.getCountryId()==0) {
            recruitmeException.addException(ErrorFor.COUNTRY_ERR.getErrorFor(), CountryError.COUNTRY_REQURED.getCountryError());
        }
        else if(CountryModel.countryIdMap.containsKey(addressDTO.getCountryId())==false) {
            recruitmeException.addException(ErrorFor.COUNTRY_ERR.getErrorFor(), CountryError.INVALID_COUNTRY_ID.getCountryError());
        }
        if(addressDTO.getStateId()==null || addressDTO.getStateId()==0) {
            recruitmeException.addException(ErrorFor.STATE_ERR.getErrorFor(),StateError.STATE_REQURED.getStateError());
        }
        else {
            List<StateDTO> stateList=StateModel.stateCountryIdMap.get(addressDTO.getCountryId());
            Boolean isStateFound=false;
            if(stateList!=null) {
                for (StateDTO state : stateList) {
                    if (state.getId() == addressDTO.getStateId()) {
                        isStateFound = true;
                        break;
                    }
                }
            }
            if(isStateFound==false) recruitmeException.addException(ErrorFor.STATE_ERR.getErrorFor(),StateError.INVALID_STATE_ID.getStateError());
        }

        if(addressDTO.getCityId()==null || addressDTO.getCityId()==0) {
            recruitmeException.addException(ErrorFor.CITY_ERR.getErrorFor(),CityError.CITY_REQUIRED.getCityError());
        }
        else {
            List<CityDTO> cityList=CityModel.cityStateIdMap.get(addressDTO.getStateId());
            Boolean isCityFound=false;
            log.debug("city id: {}",addressDTO.getCityId());
            if(cityList!=null) {
                for (CityDTO city : cityList) {
                    int tmpCityId = city.getId();
                    if (tmpCityId == addressDTO.getCityId()) {
                        isCityFound = true;
                        break;
                    }
                }
            }
            if(isCityFound==false) recruitmeException.addException(ErrorFor.CITY_ERR.getErrorFor(), CityError.INVALID_CITY_ID.getCityError());
        }

        if(addressDTO.getPinCode()==null || addressDTO.getPinCode().length()==0) {
            recruitmeException.addException(ErrorFor.PIN_CODE_ERR.getErrorFor(),AddressError.PIN_CODE_REQUIRED.getAddressError());
        }

        if(recruitmeException.getExceptions().size()>0) throw recruitmeException;
        ModelMapper modelMapper=new ModelMapper();
        Address address=modelMapper.map(addressDTO,Address.class);
        address=this.addressRepository.save(address);
        return address.getId();
    }

    @Override
    public AddressDTO findById(Integer id) {
        if(id==null) return null;
        Optional<Address> address=this.addressRepository.findById(id);
        if(address.isPresent()) {
            ModelMapper mapper=new ModelMapper();
            AddressDTO addressDTO=mapper.map(address,AddressDTO.class);
            return addressDTO;
        }
        return null;
    }

    @Override
    public void delete(Address address) {
        this.addressRepository.delete(address);
    }

}
