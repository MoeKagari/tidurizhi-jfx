package tdrz.update;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.json.JsonValue;

import tdrz.server.RawApiDataType;
import tdrz.update.UnitHandler;
import tdrz.update.handler.airbase.ApiAirbaseChangeName;
import tdrz.update.handler.airbase.ApiAirbaseExpand;
import tdrz.update.handler.airbase.ApiAirbaseInformation;
import tdrz.update.handler.airbase.ApiAirbaseSetPlane;
import tdrz.update.handler.airbase.ApiAirbaseSupply;
import tdrz.update.handler.deck.ApiDeck;
import tdrz.update.handler.deck.ApiDeckChange;
import tdrz.update.handler.deck.ApiDeckItemUseCond;
import tdrz.update.handler.deck.ApiDeckPresetDeck;
import tdrz.update.handler.deck.ApiDeckPresetDelete;
import tdrz.update.handler.deck.ApiDeckPresetRegister;
import tdrz.update.handler.deck.ApiDeckPresetSelect;
import tdrz.update.handler.deck.ApiDeckShipLock;
import tdrz.update.handler.deck.ApiDeckUpdateDeckName;
import tdrz.update.handler.kaisou.ApiKaisouMarriage;
import tdrz.update.handler.kaisou.ApiKaisouOpenExSlot;
import tdrz.update.handler.kaisou.ApiKaisouPowerUp;
import tdrz.update.handler.kaisou.ApiKaisouRemodeling;
import tdrz.update.handler.kaisou.ApiKaisouShip3;
import tdrz.update.handler.kaisou.ApiKaisouSlotDeprive;
import tdrz.update.handler.kaisou.ApiKaisouSlotExchange;
import tdrz.update.handler.kaisou.ApiKaisouSlotItemLock;
import tdrz.update.handler.kaisou.ApiKaisouSlotSet;
import tdrz.update.handler.kaisou.ApiKaisouSlotSetEx;
import tdrz.update.handler.kaisou.ApiKaisouUnsetSlotAll;
import tdrz.update.handler.kdock.ApiKdock;
import tdrz.update.handler.kdock.ApiKdockCreateShip;
import tdrz.update.handler.kdock.ApiKdockGetShip;
import tdrz.update.handler.kdock.ApiKdockSpeedChange;
import tdrz.update.handler.mission.ApiMission;
import tdrz.update.handler.mission.ApiMissionResult;
import tdrz.update.handler.mission.ApiMissionReturn;
import tdrz.update.handler.mission.ApiMissionStart;
import tdrz.update.handler.ndock.ApiNdock;
import tdrz.update.handler.ndock.ApiNdockNyukyoSpeedChange;
import tdrz.update.handler.ndock.ApiNdockNyukyoStart;
import tdrz.update.handler.other.ApiBasic;
import tdrz.update.handler.other.ApiCharge;
import tdrz.update.handler.other.ApiCombined;
import tdrz.update.handler.other.ApiCreateSlotItem;
import tdrz.update.handler.other.ApiDestroyShip;
import tdrz.update.handler.other.ApiDestroySlotItem;
import tdrz.update.handler.other.ApiEventMapRankSelect;
import tdrz.update.handler.other.ApiGetIncentive;
import tdrz.update.handler.other.ApiItemUse;
import tdrz.update.handler.other.ApiMapInfo;
import tdrz.update.handler.other.ApiMasterData;
import tdrz.update.handler.other.ApiMaterial;
import tdrz.update.handler.other.ApiPort;
import tdrz.update.handler.other.ApiRecord;
import tdrz.update.handler.other.ApiRequireInfo;
import tdrz.update.handler.other.ApiShip2;
import tdrz.update.handler.other.ApiSlotItem;
import tdrz.update.handler.other.ApiSortieConditions;
import tdrz.update.handler.other.ApiUpdateComment;
import tdrz.update.handler.other.ApiUseItem;
import tdrz.update.handler.practice.ApiPracticeChangeMatchingKind;
import tdrz.update.handler.practice.ApiPracticeEnemyInfo;
import tdrz.update.handler.practice.ApiPracticeList;
import tdrz.update.handler.quest.ApiQuestClear;
import tdrz.update.handler.quest.ApiQuestList;
import tdrz.update.handler.quest.ApiQuestStart;
import tdrz.update.handler.quest.ApiQuestStop;
import tdrz.update.handler.remodel.ApiRemodelSlot;
import tdrz.update.handler.remodel.ApiRemodelSlotList;
import tdrz.update.handler.remodel.ApiRemodelSlotListDetail;
import tdrz.update.part.AirBaseUnit.AirBaseUnitHandler;
import tdrz.update.part.BattleUnit.BattleUnitHandler;
import tdrz.update.part.DeckUnit.DeckUnitHandler;
import tdrz.update.part.KaisouUnit.KaisouUnitHandler;
import tdrz.update.part.KdockUnit.KdockUnitHandler;
import tdrz.update.part.MissionUnit.MissionUnitHandler;
import tdrz.update.part.NdockUnit.NdockUnitHandler;
import tdrz.update.part.PracticeUnit.PracticeUnitHandler;
import tdrz.update.part.QuestUnit.QuestUnitHandler;
import tdrz.update.part.RemodelUnit.RemodelUnitHandler;
import tdrz.update.part.other.AkashiUnit.AkashiUnitHandler;
import tdrz.update.part.other.MaterialUnit.MaterialUnitHandler;
import tdrz.update.part.other.MemoryUnit.MemoryUnitHandler;
import tdrz.update.part.other.PrintUnit.PrintUnitHandler;
import tdrz.update.part.other.ShipUnit.ShipUnitHandler;
import tdrz.update.part.other.SlotItemUnit.SlotItemUnitHandler;
import tdrz.update.part.other.UseItemUnit.UseItemUnitHandler;
import tdrz.update.part.other.WordUnit.WordUnitHandler;

public abstract class UnitHandler implements WordUnitHandler, MemoryUnitHandler, QuestUnitHandler, BattleUnitHandler,
		MaterialUnitHandler, PracticeUnitHandler, RemodelUnitHandler, MissionUnitHandler, PrintUnitHandler, AirBaseUnitHandler,
		DeckUnitHandler, KdockUnitHandler, NdockUnitHandler, KaisouUnitHandler,
		ShipUnitHandler, SlotItemUnitHandler, UseItemUnitHandler, AkashiUnitHandler {

	/**
	 * 此 {@link UnitHandler} 内是否有其它 {@link UnitHandler}<br>
	 * 默认{@link Collections#emptyList()}
	 */
	public List<UnitHandler> otherUnitHandler() {
		return Collections.emptyList();
	}

	/**
	 * 此 {@link UnitHandler} 是否产生了任何一样更新<br>
	 * 默认 true
	 */
	public boolean haveAnyUpdate() {
		return true;
	}

	public static final UnitHandler EMPTY_HANDLER = new UnitHandler() {
		@Override
		public boolean haveAnyUpdate() {
			return false;
		};
	};

	@FunctionalInterface
	public interface NewUnitHandler {
		public UnitHandler getUnitHandler(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data);
	}

	public static NewUnitHandler getUnitHandler(RawApiDataType type) {
		switch (type) {
			case KDOCK:
				return ApiKdock::new;
			case KDOCK_CREATESHIP:
				return ApiKdockCreateShip::new;
			case KDOCK_SPEEDCHANGE:
				return ApiKdockSpeedChange::new;
			case KDOCK_GETSHIP:
				return ApiKdockGetShip::new;

			case NDOCK:
				return ApiNdock::new;
			case NDOCK_NYUKYO_START:
				return ApiNdockNyukyoStart::new;
			case NDOCK_NYUKYO_SPEEDCHANGE:
				return ApiNdockNyukyoSpeedChange::new;

			case DECK:
				return ApiDeck::new;
			case DECK_CHANGE:
				return ApiDeckChange::new;
			case DECK_UPDATEDECKNAME:
				return ApiDeckUpdateDeckName::new;
			case DECK_SHIP_LOCK:
				return ApiDeckShipLock::new;
			case DECK_ITEMUSE_COND:
				return ApiDeckItemUseCond::new;
			case DECK_PRESET_DECK:
				return ApiDeckPresetDeck::new;
			case DECK_PRESET_SELECT:
				return ApiDeckPresetSelect::new;
			case DECK_PRESET_REGISTER:
				return ApiDeckPresetRegister::new;
			case DECK_PRESET_DELETE:
				return ApiDeckPresetDelete::new;

			case MISSION:
				return ApiMission::new;
			case MISSION_START:
				return ApiMissionStart::new;
			case MISSION_RETURN:
				return ApiMissionReturn::new;
			case MISSION_RESULT:
				return ApiMissionResult::new;

			case QUEST_LIST:
				return ApiQuestList::new;
			case QUEST_START:
				return ApiQuestStart::new;
			case QUEST_STOP:
				return ApiQuestStop::new;
			case QUEST_CLEAR:
				return ApiQuestClear::new;

			case REMODEL_SLOT:
				return ApiRemodelSlot::new;
			case REMODEL_SLOTLIST:
				return ApiRemodelSlotList::new;
			case REMODEL_SLOTLIST_DETAIL:
				return ApiRemodelSlotListDetail::new;

			case KAISOU_POWERUP:
				return ApiKaisouPowerUp::new;
			case KAISOU_SLOTITEM_LOCK:
				return ApiKaisouSlotItemLock::new;
			case KAISOU_SHIP3:
				return ApiKaisouShip3::new;
			case KAISOU_OPEN_EXSLOT:
				return ApiKaisouOpenExSlot::new;
			case KAISOU_SLOT_EXCHANGE:
				return ApiKaisouSlotExchange::new;
			case KAISOU_SLOT_DEPRIVE:
				return ApiKaisouSlotDeprive::new;
			case KAISOU_MARRIAGE:
				return ApiKaisouMarriage::new;
			case KAISOU_SLOTSET:
				return ApiKaisouSlotSet::new;
			case KAISOU_SLOTSET_EX:
				return ApiKaisouSlotSetEx::new;
			case KAISOU_UNSETSLOT_ALL:
				return ApiKaisouUnsetSlotAll::new;
			case KAISOU_REMODELING:
				return ApiKaisouRemodeling::new;

			case AIRBASE_CHANGENAME:
				return ApiAirbaseChangeName::new;
			case AIRBASE_SUPPLY:
				return ApiAirbaseSupply::new;
			case AIRBASE_EXPAND:
				return ApiAirbaseExpand::new;
			case AIRBASE_INFORMATION:
				return ApiAirbaseInformation::new;
			case AIRBASE_SETPLANE:
				return ApiAirbaseSetPlane::new;

			case PRACTICE_LIST:
				return ApiPracticeList::new;
			case PRACTICE_CHANGE_MATCHING_KIND:
				return ApiPracticeChangeMatchingKind::new;
			case PRACTICE_ENEMYINFO:
				return ApiPracticeEnemyInfo::new;

			case CHARGE:
				return ApiCharge::new;
			case CREATESLOTITEM:
				return ApiCreateSlotItem::new;
			case DESTROYSHIP:
				return ApiDestroyShip::new;
			case DESTROYSLOTITEM:
				return ApiDestroySlotItem::new;

			case COMBINED:
				return ApiCombined::new;
			case USEITEM:
				return ApiUseItem::new;
			case ITEMUSE:
				return ApiItemUse::new;
			case MASTERDATA:
				return ApiMasterData::new;
			case REQUIRE_INFO:
				return ApiRequireInfo::new;
			case MAPINFO:
				return ApiMapInfo::new;
			case MATERIAL:
				return ApiMaterial::new;
			case PORT:
				return ApiPort::new;
			case BASIC:
				return ApiBasic::new;
			case SLOT_ITEM:
				return ApiSlotItem::new;
			case SHIP2:
				return ApiShip2::new;
			case UPDATECOMMENT:
				return ApiUpdateComment::new;
			case EVENTMAP_RANK_SELECT:
				return ApiEventMapRankSelect::new;
			case SORTIE_CONDITIONS:
				return ApiSortieConditions::new;
			case RECORD:
				return ApiRecord::new;
			case GET_INCENTIVE:
				return ApiGetIncentive::new;

			case UNSETSLOT:
			case MXLTVKPYUKLH:
			case PAYCHECK:
			case PAYITEM:
			case PAYITEM_USE:
			case PICTURE_BOOK:
			case MUSIC_LIST:
			case MUSIC_PLAY:
			case RADIO_PLAY:
			case SET_PORTBGM:
			case FURNITURE_BUY:
			case FURNITURE_CHANGE:
				break;

			/*------------------------------------------------------ 战斗部分 --------------------------------------------------------------------------------------------------------*/

			case BATTLE_PRACTICE_DAY:
			case BATTLE_PRACTICE_MIDNIGHT:
			case BATTLE_PRACTICE_RESULT:

			case BATTLE_SHIPDECK:
			case BATTLE_GOBACK_PORT:
			case BATTLE_START_AIR_BASE:

			case BATTLE_START:
			case BATTLE_NEXT:

			case BATTLE_AIRBATTLE:
			case BATTLE_AIRBATTLE_LD:
			case BATTLE_DAY:
			case BATTLE_MIDNIGHT:
			case BATTLE_MIDNIGHT_SP:
			case BATTLE_RESULT:

			case COMBINEBATTLE_AIRBATTLE:
			case COMBINEBATTLE_AIRBATTLE_LD:
			case COMBINEBATTLE_MIDNIGHT_SP:
			case COMBINEBATTLE_RESULT:
			case COMBINEBATTLE_DAY:
			case COMBINEBATTLE_DAY_WATER:
			case COMBINEBATTLE_MIDNIGHT:
			case COMBINEBATTLE_EC_DAY:
			case COMBINEBATTLE_EACH_DAY:
			case COMBINEBATTLE_EACH_DAY_WATER:
			case COMBINEBATTLE_EC_MIDNIGHT:
		}
		return (unitManager, time, fields, api_data) -> UnitHandler.EMPTY_HANDLER;
	}
}
