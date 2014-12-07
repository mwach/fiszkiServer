package mawa.mobica.com.util;

import java.util.List;

public abstract class ResourceHelper<DB, DTO> {

	public abstract DTO toDto(DB dictionary);
	public abstract DB fromDto(DTO dictionary);
	public abstract List<DTO> toDto(List<DB> dictionary);

}
