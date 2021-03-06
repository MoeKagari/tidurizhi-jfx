package tdrz.update.handler;

import java.util.List;
import java.util.Map;

import javax.json.JsonValue;

import tdrz.server.RawApiDataType;
import tdrz.update.UnitManager;
import tdrz.update.handler.airbase.ApiAirbaseChangeName;
import tdrz.update.handler.airbase.ApiAirbaseExpand;
import tdrz.update.handler.airbase.ApiAirbaseInformation;
import tdrz.update.handler.airbase.ApiAirbaseSetAction;
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
import tdrz.update.handler.other.ApiOtherBasic;
import tdrz.update.handler.other.ApiOtherCharge;
import tdrz.update.handler.other.ApiOtherCombined;
import tdrz.update.handler.other.ApiOtherCreateSlotItem;
import tdrz.update.handler.other.ApiOtherDestroyShip;
import tdrz.update.handler.other.ApiOtherDestroySlotItem;
import tdrz.update.handler.other.ApiOtherEventMapRankSelect;
import tdrz.update.handler.other.ApiOtherGetIncentive;
import tdrz.update.handler.other.ApiOtherItemUse;
import tdrz.update.handler.other.ApiOtherMapInfo;
import tdrz.update.handler.other.ApiOtherMasterData;
import tdrz.update.handler.other.ApiOtherMaterial;
import tdrz.update.handler.other.ApiOtherPort;
import tdrz.update.handler.other.ApiOtherRecord;
import tdrz.update.handler.other.ApiOtherRequireInfo;
import tdrz.update.handler.other.ApiOtherShip2;
import tdrz.update.handler.other.ApiOtherSlotItem;
import tdrz.update.handler.other.ApiOtherSortieConditions;
import tdrz.update.handler.other.ApiOtherUpdateComment;
import tdrz.update.handler.other.ApiOtherUseItem;
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
import tdrz.update.unit.UnitAirBase.UnitHandlerAirBase;
import tdrz.update.unit.UnitAkashi.UnitHandlerAkashi;
import tdrz.update.unit.UnitBattle.UnitHandlerBattle;
import tdrz.update.unit.UnitDeck.UnitHandlerDeck;
import tdrz.update.unit.UnitKdock.UnitHandlerKdock;
import tdrz.update.unit.UnitMaterial.UnitHandlerMaterial;
import tdrz.update.unit.UnitMemory.UnitHandlerMemory;
import tdrz.update.unit.UnitNdock.UnitHandlerNdock;
import tdrz.update.unit.UnitPractice.UnitPracticeHandler;
import tdrz.update.unit.UnitPrint.PrintItem;
import tdrz.update.unit.UnitPrint.UnitHandlerPrint;
import tdrz.update.unit.UnitProperty.UnitHandlerProperty;
import tdrz.update.unit.UnitQuest.UnitHandlerQuest;
import tdrz.update.unit.UnitWord.UnitHandlerWord;
import tool.function.FunctionUtils;

public abstract class UnitHandler implements
		UnitHandlerWord, UnitHandlerMemory, UnitHandlerQuest, UnitHandlerBattle,
		UnitHandlerMaterial, UnitHandlerPrint, UnitPracticeHandler,
		UnitHandlerAirBase, UnitHandlerDeck, UnitHandlerKdock, UnitHandlerNdock,
		UnitHandlerProperty, UnitHandlerAkashi {

	/** 此 {@link UnitHandler} 内是否有其它 {@link UnitHandler} */
	public List<UnitHandler> otherUnitHandler() {
		return FunctionUtils.emptyList();
	}

	/** 此 {@link UnitHandler} 是否产生了任何一样更新 */
	public boolean haveAnyUpdate() {
		return true;
	}

	@Override
	public PrintItem getPrintItem() {
		return new PrintItem() {
			@Override
			public String toString() {
				return UnitHandler.this.toString();
			}
		};
	}

	public static UnitHandler getUnitHandler(RawApiDataType type, UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		return getUnitHandler(type).getUnitHandler(unitManager, time, fields, api_data);
	}

	private static NewUnitHandler getUnitHandler(RawApiDataType type) {
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
			case AIRBASE_SETACTION:
				return ApiAirbaseSetAction::new;

			case PRACTICE_LIST:
				return ApiPracticeList::new;
			case PRACTICE_CHANGE_MATCHING_KIND:
				return ApiPracticeChangeMatchingKind::new;
			case PRACTICE_ENEMYINFO:
				return ApiPracticeEnemyInfo::new;

			case CHARGE:
				return ApiOtherCharge::new;
			case CREATESLOTITEM:
				return ApiOtherCreateSlotItem::new;
			case DESTROYSHIP:
				return ApiOtherDestroyShip::new;
			case DESTROYSLOTITEM:
				return ApiOtherDestroySlotItem::new;

			case COMBINED:
				return ApiOtherCombined::new;
			case USEITEM:
				return ApiOtherUseItem::new;
			case ITEMUSE:
				return ApiOtherItemUse::new;
			case MASTERDATA:
				return ApiOtherMasterData::new;
			case REQUIRE_INFO:
				return ApiOtherRequireInfo::new;
			case MAPINFO:
				return ApiOtherMapInfo::new;
			case MATERIAL:
				return ApiOtherMaterial::new;
			case PORT:
				return ApiOtherPort::new;
			case BASIC:
				return ApiOtherBasic::new;
			case SLOT_ITEM:
				return ApiOtherSlotItem::new;
			case SHIP2:
				return ApiOtherShip2::new;
			case UPDATECOMMENT:
				return ApiOtherUpdateComment::new;
			case EVENTMAP_RANK_SELECT:
				return ApiOtherEventMapRankSelect::new;
			case SORTIE_CONDITIONS:
				return ApiOtherSortieConditions::new;
			case RECORD:
				return ApiOtherRecord::new;
			case GET_INCENTIVE:
				return ApiOtherGetIncentive::new;

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
		return (unitManager, time, fields, api_data) -> UnitHandler.EMPTY_UNITHANDLER;
	}

	private static final UnitHandler EMPTY_UNITHANDLER = new UnitHandler() {
		@Override
		public boolean haveAnyUpdate() {
			return false;
		}
	};

	@FunctionalInterface
	private interface NewUnitHandler {
		public UnitHandler getUnitHandler(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data);
	}
}
