
#import <Foundation/Foundation.h>
#import "CDVDMP.h"
#import "KruxConsentCallbackImpl.h"

@implementation CDVDMP

static KruxTracker *kt;

+ (KruxTracker *)getKruxTracker {
    return kt;
}

- (void)initialize:(CDVInvokedUrlCommand*)command
{
    // TODO - Take id from app data: NSString* a = [[command arguments] objectAtIndex:0];
    KruxConsentCallbackImpl *consentCallback = [[KruxConsentCallbackImpl alloc] init];
    kt = [KruxTracker sharedEventTrackerWithConfigId:@"uwgimqbe0" debugFlag:true dryRunFlag:false consentCallback:consentCallback];
    
    CDVPluginResult* result = [CDVPluginResult
                               resultWithStatus:CDVCommandStatus_OK
                               messageAsString:@"Init Krux OK"];
    
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

- (void)sendRequests:(CDVInvokedUrlCommand*)command
{
    // TODO
    NSDictionary *consentSetAttributes = [self getConsentAttributes];
    NSDictionary *idAttributes = [self getIdParameters];
    NSDictionary *consentGetAttributes = [self getIdParameters];
    [self addPolicyRegimeParameter:consentGetAttributes];
    [self addPolicyRegimeParameter:consentSetAttributes];
    
    [kt consentGetRequest:consentGetAttributes];
    [kt consentSetRequest:consentSetAttributes];
    [kt consumerRemoveRequest:idAttributes];
    [kt consumerPortabilityRequest:idAttributes];
    
    CDVPluginResult* result = [CDVPluginResult
                               resultWithStatus:CDVCommandStatus_OK
                               messageAsString:@"Send Krux Requests OK"];
    
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

- (void)trackPage:(CDVInvokedUrlCommand *)command
{
    // TODO - Take data from app: NSString* a = [[command arguments] objectAtIndex:0];
    
    // Create a dictionary of page attributes
    NSDictionary *pageAttr = [[NSDictionary alloc] initWithObjectsAndKeys: @"/home", @"path", @"home", @"pageType", nil];
    // Create a dictionary of user attributes
    NSDictionary *userAttr = [[NSDictionary alloc] initWithObjectsAndKeys: @"prueba@okode.com", @"email", nil];
    // Initialize Krux Tracker
    [kt trackPageView:@"Home_Page" pageAttributes:pageAttr userAttributes:userAttr];
    
    CDVPluginResult* result = [CDVPluginResult
                               resultWithStatus:CDVCommandStatus_OK
                               messageAsString:@"Track Page Krux OK"];
    
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

- (void)fireEvent:(CDVInvokedUrlCommand *)command
{
    // NSString* a = [[command arguments] objectAtIndex:0];
    NSDictionary *attrs = [[NSDictionary alloc] initWithObjectsAndKeys: @"click", @"eventAction", @"button", @"eventCategory", @"Connect to dmp", @"eventLabel", nil];
    NSError *err;
    [kt fireEvent:@"HsdfRt12" eventAttributes:attrs withError:&err];
    
    CDVPluginResult* result = [CDVPluginResult
                               resultWithStatus:CDVCommandStatus_OK
                               messageAsString:@"Fire Event Krux OK"];
    
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

- (void) addPolicyRegimeParameter: (NSDictionary *) attributes
{
    [attributes setValue:@"gdpr" forKey:@"pr"];
}

- (NSDictionary *) getIdParameters
{
    NSDictionary *consentAttributes = [[NSDictionary alloc] init];
    [consentAttributes setValue:@"<idfa-value>" forKey:@"idv"];
    [consentAttributes setValue:@"idfa" forKey:@"dt"];
    [consentAttributes setValue:@"device" forKey:@"idt"];
    
    return consentAttributes;
}

- (NSDictionary *) getConsentAttributes
{
    NSMutableDictionary *consentAttributes = [NSMutableDictionary dictionaryWithObjectsAndKeys:
                                              [NSNumber numberWithInt:1], @"dc",
                                              [NSNumber numberWithInt:1], @"cd",
                                              [NSNumber numberWithInt:1], @"tg",
                                              [NSNumber numberWithInt:1], @"al",
                                              [NSNumber numberWithInt:1], @"sh",
                                              [NSNumber numberWithInt:1], @"re", nil];
    [consentAttributes addEntriesFromDictionary:[self getIdParameters]];
    return consentAttributes;
}

@end
