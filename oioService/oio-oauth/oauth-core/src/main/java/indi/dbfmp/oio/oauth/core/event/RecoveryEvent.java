package indi.dbfmp.oio.oauth.core.event;

/**
 * <p>
 *  恢复通知接口
 * </p>
 *
 * @author dbfmp
 * @name: RecoveryEvent
 * @since 2020/10/29 8:33 下午
 */
public interface RecoveryEvent<T extends BaseEvent> {

    /**
     * 恢复通知操作
     *
     * 把json格式的event事件转换为实际参数，进行调用
     *
     * @param eventId 事件id
     * @param eventJsonParams eventJsonParams
     */
    void recoveryEventAction(String eventId,String eventJsonParams);

    void eventAction(T event);

}
