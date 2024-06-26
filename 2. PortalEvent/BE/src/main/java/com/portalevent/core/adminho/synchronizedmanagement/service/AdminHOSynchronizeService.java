package com.portalevent.core.adminho.synchronizedmanagement.service;

public interface AdminHOSynchronizeService {

    void synchronizeAllIdentity();

    void synchronizeCampusIdentity();

    void synchronizeDepartmentIdentity();

    void synchronizeMajorIdentity();

    void synchronizeDepartmentCampusIdentity();

    void synchronizeMajorCampusIdentity();

    void synchronizeSemestersIdentity();


}
