// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Chat.proto

#ifndef PROTOBUF_Chat_2eproto__INCLUDED
#define PROTOBUF_Chat_2eproto__INCLUDED

#include <string>

#include <google/protobuf/stubs/common.h>

#if GOOGLE_PROTOBUF_VERSION < 2005000
#error This file was generated by a newer version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please update
#error your headers.
#endif
#if 2005000 < GOOGLE_PROTOBUF_MIN_PROTOC_VERSION
#error This file was generated by an older version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please
#error regenerate this file with a newer version of protoc.
#endif

#include <google/protobuf/generated_message_util.h>
#include <google/protobuf/message.h>
#include <google/protobuf/repeated_field.h>
#include <google/protobuf/extension_set.h>
#include <google/protobuf/unknown_field_set.h>
// @@protoc_insertion_point(includes)

// Internal implementation detail -- do not call these.
void  protobuf_AddDesc_Chat_2eproto();
void protobuf_AssignDesc_Chat_2eproto();
void protobuf_ShutdownFile_Chat_2eproto();

class HSSendChat;
class HSPushChat;

// ===================================================================

class HSSendChat : public ::google::protobuf::Message {
 public:
  HSSendChat();
  virtual ~HSSendChat();

  HSSendChat(const HSSendChat& from);

  inline HSSendChat& operator=(const HSSendChat& from) {
    CopyFrom(from);
    return *this;
  }

  inline const ::google::protobuf::UnknownFieldSet& unknown_fields() const {
    return _unknown_fields_;
  }

  inline ::google::protobuf::UnknownFieldSet* mutable_unknown_fields() {
    return &_unknown_fields_;
  }

  static const ::google::protobuf::Descriptor* descriptor();
  static const HSSendChat& default_instance();

  void Swap(HSSendChat* other);

  // implements Message ----------------------------------------------

  HSSendChat* New() const;
  void CopyFrom(const ::google::protobuf::Message& from);
  void MergeFrom(const ::google::protobuf::Message& from);
  void CopyFrom(const HSSendChat& from);
  void MergeFrom(const HSSendChat& from);
  void Clear();
  bool IsInitialized() const;

  int ByteSize() const;
  bool MergePartialFromCodedStream(
      ::google::protobuf::io::CodedInputStream* input);
  void SerializeWithCachedSizes(
      ::google::protobuf::io::CodedOutputStream* output) const;
  ::google::protobuf::uint8* SerializeWithCachedSizesToArray(::google::protobuf::uint8* output) const;
  int GetCachedSize() const { return _cached_size_; }
  private:
  void SharedCtor();
  void SharedDtor();
  void SetCachedSize(int size) const;
  public:

  ::google::protobuf::Metadata GetMetadata() const;

  // nested types ----------------------------------------------------

  // accessors -------------------------------------------------------

  // required string chatMsg = 1;
  inline bool has_chatmsg() const;
  inline void clear_chatmsg();
  static const int kChatMsgFieldNumber = 1;
  inline const ::std::string& chatmsg() const;
  inline void set_chatmsg(const ::std::string& value);
  inline void set_chatmsg(const char* value);
  inline void set_chatmsg(const char* value, size_t size);
  inline ::std::string* mutable_chatmsg();
  inline ::std::string* release_chatmsg();
  inline void set_allocated_chatmsg(::std::string* chatmsg);

  // @@protoc_insertion_point(class_scope:HSSendChat)
 private:
  inline void set_has_chatmsg();
  inline void clear_has_chatmsg();

  ::google::protobuf::UnknownFieldSet _unknown_fields_;

  ::std::string* chatmsg_;

  mutable int _cached_size_;
  ::google::protobuf::uint32 _has_bits_[(1 + 31) / 32];

  friend void  protobuf_AddDesc_Chat_2eproto();
  friend void protobuf_AssignDesc_Chat_2eproto();
  friend void protobuf_ShutdownFile_Chat_2eproto();

  void InitAsDefaultInstance();
  static HSSendChat* default_instance_;
};
// -------------------------------------------------------------------

class HSPushChat : public ::google::protobuf::Message {
 public:
  HSPushChat();
  virtual ~HSPushChat();

  HSPushChat(const HSPushChat& from);

  inline HSPushChat& operator=(const HSPushChat& from) {
    CopyFrom(from);
    return *this;
  }

  inline const ::google::protobuf::UnknownFieldSet& unknown_fields() const {
    return _unknown_fields_;
  }

  inline ::google::protobuf::UnknownFieldSet* mutable_unknown_fields() {
    return &_unknown_fields_;
  }

  static const ::google::protobuf::Descriptor* descriptor();
  static const HSPushChat& default_instance();

  void Swap(HSPushChat* other);

  // implements Message ----------------------------------------------

  HSPushChat* New() const;
  void CopyFrom(const ::google::protobuf::Message& from);
  void MergeFrom(const ::google::protobuf::Message& from);
  void CopyFrom(const HSPushChat& from);
  void MergeFrom(const HSPushChat& from);
  void Clear();
  bool IsInitialized() const;

  int ByteSize() const;
  bool MergePartialFromCodedStream(
      ::google::protobuf::io::CodedInputStream* input);
  void SerializeWithCachedSizes(
      ::google::protobuf::io::CodedOutputStream* output) const;
  ::google::protobuf::uint8* SerializeWithCachedSizesToArray(::google::protobuf::uint8* output) const;
  int GetCachedSize() const { return _cached_size_; }
  private:
  void SharedCtor();
  void SharedDtor();
  void SetCachedSize(int size) const;
  public:

  ::google::protobuf::Metadata GetMetadata() const;

  // nested types ----------------------------------------------------

  // accessors -------------------------------------------------------

  // required int32 type = 1;
  inline bool has_type() const;
  inline void clear_type();
  static const int kTypeFieldNumber = 1;
  inline ::google::protobuf::int32 type() const;
  inline void set_type(::google::protobuf::int32 value);

  // required int32 playerId = 2;
  inline bool has_playerid() const;
  inline void clear_playerid();
  static const int kPlayerIdFieldNumber = 2;
  inline ::google::protobuf::int32 playerid() const;
  inline void set_playerid(::google::protobuf::int32 value);

  // required string name = 3;
  inline bool has_name() const;
  inline void clear_name();
  static const int kNameFieldNumber = 3;
  inline const ::std::string& name() const;
  inline void set_name(const ::std::string& value);
  inline void set_name(const char* value);
  inline void set_name(const char* value, size_t size);
  inline ::std::string* mutable_name();
  inline ::std::string* release_name();
  inline void set_allocated_name(::std::string* name);

  // required string chatMsg = 4;
  inline bool has_chatmsg() const;
  inline void clear_chatmsg();
  static const int kChatMsgFieldNumber = 4;
  inline const ::std::string& chatmsg() const;
  inline void set_chatmsg(const ::std::string& value);
  inline void set_chatmsg(const char* value);
  inline void set_chatmsg(const char* value, size_t size);
  inline ::std::string* mutable_chatmsg();
  inline ::std::string* release_chatmsg();
  inline void set_allocated_chatmsg(::std::string* chatmsg);

  // @@protoc_insertion_point(class_scope:HSPushChat)
 private:
  inline void set_has_type();
  inline void clear_has_type();
  inline void set_has_playerid();
  inline void clear_has_playerid();
  inline void set_has_name();
  inline void clear_has_name();
  inline void set_has_chatmsg();
  inline void clear_has_chatmsg();

  ::google::protobuf::UnknownFieldSet _unknown_fields_;

  ::google::protobuf::int32 type_;
  ::google::protobuf::int32 playerid_;
  ::std::string* name_;
  ::std::string* chatmsg_;

  mutable int _cached_size_;
  ::google::protobuf::uint32 _has_bits_[(4 + 31) / 32];

  friend void  protobuf_AddDesc_Chat_2eproto();
  friend void protobuf_AssignDesc_Chat_2eproto();
  friend void protobuf_ShutdownFile_Chat_2eproto();

  void InitAsDefaultInstance();
  static HSPushChat* default_instance_;
};
// ===================================================================


// ===================================================================

// HSSendChat

// required string chatMsg = 1;
inline bool HSSendChat::has_chatmsg() const {
  return (_has_bits_[0] & 0x00000001u) != 0;
}
inline void HSSendChat::set_has_chatmsg() {
  _has_bits_[0] |= 0x00000001u;
}
inline void HSSendChat::clear_has_chatmsg() {
  _has_bits_[0] &= ~0x00000001u;
}
inline void HSSendChat::clear_chatmsg() {
  if (chatmsg_ != &::google::protobuf::internal::kEmptyString) {
    chatmsg_->clear();
  }
  clear_has_chatmsg();
}
inline const ::std::string& HSSendChat::chatmsg() const {
  return *chatmsg_;
}
inline void HSSendChat::set_chatmsg(const ::std::string& value) {
  set_has_chatmsg();
  if (chatmsg_ == &::google::protobuf::internal::kEmptyString) {
    chatmsg_ = new ::std::string;
  }
  chatmsg_->assign(value);
}
inline void HSSendChat::set_chatmsg(const char* value) {
  set_has_chatmsg();
  if (chatmsg_ == &::google::protobuf::internal::kEmptyString) {
    chatmsg_ = new ::std::string;
  }
  chatmsg_->assign(value);
}
inline void HSSendChat::set_chatmsg(const char* value, size_t size) {
  set_has_chatmsg();
  if (chatmsg_ == &::google::protobuf::internal::kEmptyString) {
    chatmsg_ = new ::std::string;
  }
  chatmsg_->assign(reinterpret_cast<const char*>(value), size);
}
inline ::std::string* HSSendChat::mutable_chatmsg() {
  set_has_chatmsg();
  if (chatmsg_ == &::google::protobuf::internal::kEmptyString) {
    chatmsg_ = new ::std::string;
  }
  return chatmsg_;
}
inline ::std::string* HSSendChat::release_chatmsg() {
  clear_has_chatmsg();
  if (chatmsg_ == &::google::protobuf::internal::kEmptyString) {
    return NULL;
  } else {
    ::std::string* temp = chatmsg_;
    chatmsg_ = const_cast< ::std::string*>(&::google::protobuf::internal::kEmptyString);
    return temp;
  }
}
inline void HSSendChat::set_allocated_chatmsg(::std::string* chatmsg) {
  if (chatmsg_ != &::google::protobuf::internal::kEmptyString) {
    delete chatmsg_;
  }
  if (chatmsg) {
    set_has_chatmsg();
    chatmsg_ = chatmsg;
  } else {
    clear_has_chatmsg();
    chatmsg_ = const_cast< ::std::string*>(&::google::protobuf::internal::kEmptyString);
  }
}

// -------------------------------------------------------------------

// HSPushChat

// required int32 type = 1;
inline bool HSPushChat::has_type() const {
  return (_has_bits_[0] & 0x00000001u) != 0;
}
inline void HSPushChat::set_has_type() {
  _has_bits_[0] |= 0x00000001u;
}
inline void HSPushChat::clear_has_type() {
  _has_bits_[0] &= ~0x00000001u;
}
inline void HSPushChat::clear_type() {
  type_ = 0;
  clear_has_type();
}
inline ::google::protobuf::int32 HSPushChat::type() const {
  return type_;
}
inline void HSPushChat::set_type(::google::protobuf::int32 value) {
  set_has_type();
  type_ = value;
}

// required int32 playerId = 2;
inline bool HSPushChat::has_playerid() const {
  return (_has_bits_[0] & 0x00000002u) != 0;
}
inline void HSPushChat::set_has_playerid() {
  _has_bits_[0] |= 0x00000002u;
}
inline void HSPushChat::clear_has_playerid() {
  _has_bits_[0] &= ~0x00000002u;
}
inline void HSPushChat::clear_playerid() {
  playerid_ = 0;
  clear_has_playerid();
}
inline ::google::protobuf::int32 HSPushChat::playerid() const {
  return playerid_;
}
inline void HSPushChat::set_playerid(::google::protobuf::int32 value) {
  set_has_playerid();
  playerid_ = value;
}

// required string name = 3;
inline bool HSPushChat::has_name() const {
  return (_has_bits_[0] & 0x00000004u) != 0;
}
inline void HSPushChat::set_has_name() {
  _has_bits_[0] |= 0x00000004u;
}
inline void HSPushChat::clear_has_name() {
  _has_bits_[0] &= ~0x00000004u;
}
inline void HSPushChat::clear_name() {
  if (name_ != &::google::protobuf::internal::kEmptyString) {
    name_->clear();
  }
  clear_has_name();
}
inline const ::std::string& HSPushChat::name() const {
  return *name_;
}
inline void HSPushChat::set_name(const ::std::string& value) {
  set_has_name();
  if (name_ == &::google::protobuf::internal::kEmptyString) {
    name_ = new ::std::string;
  }
  name_->assign(value);
}
inline void HSPushChat::set_name(const char* value) {
  set_has_name();
  if (name_ == &::google::protobuf::internal::kEmptyString) {
    name_ = new ::std::string;
  }
  name_->assign(value);
}
inline void HSPushChat::set_name(const char* value, size_t size) {
  set_has_name();
  if (name_ == &::google::protobuf::internal::kEmptyString) {
    name_ = new ::std::string;
  }
  name_->assign(reinterpret_cast<const char*>(value), size);
}
inline ::std::string* HSPushChat::mutable_name() {
  set_has_name();
  if (name_ == &::google::protobuf::internal::kEmptyString) {
    name_ = new ::std::string;
  }
  return name_;
}
inline ::std::string* HSPushChat::release_name() {
  clear_has_name();
  if (name_ == &::google::protobuf::internal::kEmptyString) {
    return NULL;
  } else {
    ::std::string* temp = name_;
    name_ = const_cast< ::std::string*>(&::google::protobuf::internal::kEmptyString);
    return temp;
  }
}
inline void HSPushChat::set_allocated_name(::std::string* name) {
  if (name_ != &::google::protobuf::internal::kEmptyString) {
    delete name_;
  }
  if (name) {
    set_has_name();
    name_ = name;
  } else {
    clear_has_name();
    name_ = const_cast< ::std::string*>(&::google::protobuf::internal::kEmptyString);
  }
}

// required string chatMsg = 4;
inline bool HSPushChat::has_chatmsg() const {
  return (_has_bits_[0] & 0x00000008u) != 0;
}
inline void HSPushChat::set_has_chatmsg() {
  _has_bits_[0] |= 0x00000008u;
}
inline void HSPushChat::clear_has_chatmsg() {
  _has_bits_[0] &= ~0x00000008u;
}
inline void HSPushChat::clear_chatmsg() {
  if (chatmsg_ != &::google::protobuf::internal::kEmptyString) {
    chatmsg_->clear();
  }
  clear_has_chatmsg();
}
inline const ::std::string& HSPushChat::chatmsg() const {
  return *chatmsg_;
}
inline void HSPushChat::set_chatmsg(const ::std::string& value) {
  set_has_chatmsg();
  if (chatmsg_ == &::google::protobuf::internal::kEmptyString) {
    chatmsg_ = new ::std::string;
  }
  chatmsg_->assign(value);
}
inline void HSPushChat::set_chatmsg(const char* value) {
  set_has_chatmsg();
  if (chatmsg_ == &::google::protobuf::internal::kEmptyString) {
    chatmsg_ = new ::std::string;
  }
  chatmsg_->assign(value);
}
inline void HSPushChat::set_chatmsg(const char* value, size_t size) {
  set_has_chatmsg();
  if (chatmsg_ == &::google::protobuf::internal::kEmptyString) {
    chatmsg_ = new ::std::string;
  }
  chatmsg_->assign(reinterpret_cast<const char*>(value), size);
}
inline ::std::string* HSPushChat::mutable_chatmsg() {
  set_has_chatmsg();
  if (chatmsg_ == &::google::protobuf::internal::kEmptyString) {
    chatmsg_ = new ::std::string;
  }
  return chatmsg_;
}
inline ::std::string* HSPushChat::release_chatmsg() {
  clear_has_chatmsg();
  if (chatmsg_ == &::google::protobuf::internal::kEmptyString) {
    return NULL;
  } else {
    ::std::string* temp = chatmsg_;
    chatmsg_ = const_cast< ::std::string*>(&::google::protobuf::internal::kEmptyString);
    return temp;
  }
}
inline void HSPushChat::set_allocated_chatmsg(::std::string* chatmsg) {
  if (chatmsg_ != &::google::protobuf::internal::kEmptyString) {
    delete chatmsg_;
  }
  if (chatmsg) {
    set_has_chatmsg();
    chatmsg_ = chatmsg;
  } else {
    clear_has_chatmsg();
    chatmsg_ = const_cast< ::std::string*>(&::google::protobuf::internal::kEmptyString);
  }
}


// @@protoc_insertion_point(namespace_scope)

#ifndef SWIG
namespace google {
namespace protobuf {


}  // namespace google
}  // namespace protobuf
#endif  // SWIG

// @@protoc_insertion_point(global_scope)

#endif  // PROTOBUF_Chat_2eproto__INCLUDED
